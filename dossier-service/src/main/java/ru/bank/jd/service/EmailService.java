package ru.bank.jd.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.StatementDto;
import ru.bank.jd.util.CreditDocument;
import ru.bank.jd.util.EmailMessageFactory;
import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendSimpleEmail(EmailMessage data) {
        SimpleMailMessage message = EmailMessageFactory.getSimpleMessage(data);
        javaMailSender.send(message);
        log.info("email Successfully sent");
    }

    public void sendSesCode(EmailMessage data, String sesCode) {
        SimpleMailMessage message = EmailMessageFactory.getMessageSendSesCode(data, sesCode);
        javaMailSender.send(message);
        log.info("email Successfully sent");
    }

    public void sendMimeMessageDocument(EmailMessage data, StatementDto statementDto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        File file = CreditDocument.createDocument(statementDto);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(data.getAddress());
            helper.setText("Здравствуйте! Ваш кредитный договор во вложение, просьба ознакомиться:");
            helper.setSubject("Кредитный договор");
            helper.addAttachment("document.txt", file);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error send: {}", e.getMessage());
            throw new RuntimeException("Error send");
        }
        log.info("email Successfully sent");
        file.delete();
    }

}
