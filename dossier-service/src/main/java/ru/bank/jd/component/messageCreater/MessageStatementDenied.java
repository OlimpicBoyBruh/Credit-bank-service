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
public class MessageStatementDenied implements MessageCreator {
    private final JavaMailSender javaSender;
    @Override
    public MimeMessage getMessage(EmailMessage emailMessage) {
        try {
            MimeMessage mimeMessage = javaSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setTo(emailMessage.getAddress());
            helper.setSubject("Кредитная заявка");
            helper.setText("Здравствуйте! по вашей заявке на кредит принято отрицательное решение. "
                    + "Повторно подать заявление возможно не ранее, чем через 60 дней.");
            return mimeMessage;
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Theme getTheme() {
        return Theme.STATEMENT_DENIED;
    }

}
