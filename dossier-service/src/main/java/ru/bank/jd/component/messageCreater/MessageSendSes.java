package ru.bank.jd.component.messageCreater;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.bank.jd.component.RequestDealServiceRest;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.enumerated.Theme;

@Component
@RequiredArgsConstructor
public class MessageSendSes implements MessageCreator {
    private final RequestDealServiceRest requestDealServiceRest;
    private final JavaMailSender javaSender;

    @Override
    public MimeMessage getMessage(EmailMessage emailMessage) {
        String sesCode = requestDealServiceRest.getStatementDto(emailMessage.getStatementId()).getSesCode();
        try {
            MimeMessage mimeMessage = javaSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);

            helper.setTo(emailMessage.getAddress());
            helper.setSubject("Подписание документов");
            helper.setText("Здравствуйте! для подписания документов, просьба перейти по ссылке: "
                    + "\"ссылка\" \n" + "Ваш код: " + sesCode);
            return mimeMessage;
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Theme getTheme() {
        return Theme.SEND_SES;
    }
}
