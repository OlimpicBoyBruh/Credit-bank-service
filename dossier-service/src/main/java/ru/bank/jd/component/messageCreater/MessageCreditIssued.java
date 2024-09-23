package ru.bank.jd.component.messageCreater;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.enumerated.Theme;

@Component
@RequiredArgsConstructor
public class MessageCreditIssued implements MessageCreator {
    private final JavaMailSender mailSender;

    @Override
    public MimeMessage getMessage(EmailMessage emailMessage) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);

            helper.setTo(emailMessage.getAddress());
            helper.setSubject("Кредит выдан");
            helper.setText("Здравствуйте! кредит №" + emailMessage.getStatementId() + " успешно зачислен на ваш счет.");
            return mimeMessage;
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Theme getTheme() {
        return Theme.CREDIT_ISSUED;
    }
}
