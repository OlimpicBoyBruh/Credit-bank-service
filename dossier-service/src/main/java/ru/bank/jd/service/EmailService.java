package ru.bank.jd.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.StatementDto;
import ru.bank.jd.exception.SendMessageException;
import ru.bank.jd.util.CreditDocument;
import ru.bank.jd.util.EmailMessageFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendSimpleEmail(EmailMessage data) {
        log.info("Received email {}", data);
        try {
            SimpleMailMessage message = EmailMessageFactory.getSimpleMessage(data);
            javaMailSender.send(message);
            log.info("email sendSimpleEmail Successfully sent");
        } catch (MailException exception) {
            log.error("ошибка отправки email в методе sendSimpleEmail: {}", exception.getMessage());
            throw exception;
        }
    }

    public void sendSesCode(EmailMessage data, String sesCode) {
        log.info("Received email {}", data);
        try {
            SimpleMailMessage message = EmailMessageFactory.getMessageSendSesCode(data, sesCode);
            javaMailSender.send(message);
            log.info("email sendSesCode Successfully sent");
        } catch (MailException exception) {
            log.error("ошибка отправки email в методе sendSesCode: {}", exception.getMessage());
            throw exception;
        }
    }

    public void sendMimeMessageDocument(EmailMessage data, StatementDto statementDto) {
        try {
            File file = CreditDocument.createDocument(statementDto);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(data.getAddress());
            helper.setText("Здравствуйте! Ваш кредитный договор во вложение, просьба ознакомиться:");
            helper.setSubject("Кредитный договор");
            helper.addAttachment("document.txt", file);

            javaMailSender.send(message);

            if (file.exists()) {
                Files.delete(file.toPath());
            }

            log.info("Email sendMimeMessageDocument successfully sent");
        } catch (MessagingException | IOException e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new SendMessageException("Error sending email");
        }
    }
}