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
public class MessageFinishRegistration implements MessageCreator {

    private final JavaMailSender javaSender;

    @Override
    public MimeMessage getMessage(EmailMessage emailMessage) {
        try {
            MimeMessage message = javaSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setTo(emailMessage.getAddress());
            helper.setSubject("Завершение оформления заявления");
            helper.setText("Здравствуйте! Для завершения оформления вашей заявки," +
                    " просим заполнить анкету по ссылке: " + "\"ссылка\"");
            return message;
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Theme getTheme() {
        return Theme.FINISH_REGISTRATION;
    }
}
