package ru.bank.jd.component.messageCreater;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.bank.jd.component.RequestDealServiceRest;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.StatementDto;
import ru.bank.jd.dto.enumerated.Theme;
import ru.bank.jd.exception.SendMessageException;
import ru.bank.jd.util.CreditDocument;
import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSendDocument implements MessageCreator {
    public final RequestDealServiceRest requestDealServiceRest;
    private final JavaMailSender javaMailSender;

    @Override
    public MimeMessage getMessage(EmailMessage emailMessage) {
        StatementDto statementDto = requestDealServiceRest.getStatementDto(emailMessage.getStatementId());
        try {
            File file = CreditDocument.createDocument(statementDto);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(emailMessage.getAddress());
            helper.setText("Здравствуйте! Ваш кредитный договор во вложение, просьба ознакомиться:");
            helper.setSubject("Кредитный договор");
            helper.addAttachment("document.txt", file);
            requestDealServiceRest.updateStatusHistory(emailMessage.getStatementId());
            return mimeMessage;
        } catch (MessagingException exception) {
            log.error("Error sending email: {}", exception.getMessage());
            throw new SendMessageException("Error sending email");
        }
    }

    @Override
    public Theme getTheme() {
        return Theme.SEND_DOCUMENTS;
    }
}
