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
public class MessageCreateDocuments implements MessageCreator {
    private final JavaMailSender mailSender;

    @Override
    public MimeMessage getMessage(EmailMessage emailMessage) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(emailMessage.getAddress());
            helper.setSubject("Одобренная кредитная заявка");
            helper.setText("Здравствуйте! Для оформления документов, просьба перейти по ссылке: "
                    + "\"ссылка\"");
            return mimeMessage;
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public Theme getTheme() {
        return Theme.CREATE_DOCUMENTS;
    }
}
