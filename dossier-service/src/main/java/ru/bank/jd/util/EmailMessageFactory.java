package ru.bank.jd.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import ru.bank.jd.dto.EmailMessage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailMessageFactory {
    public static SimpleMailMessage getSimpleMessage(EmailMessage emailMessage) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailMessage.getAddress());

        switch (emailMessage.getTheme()) {
            case FINISH_REGISTRATION:
                simpleMailMessage.setSubject("Завершение оформления заявления");
                simpleMailMessage.setText("Здравствуйте! Для завершения оформления вашей заявки," +
                        " просим заполнить анкету по ссылке: " + "\"ссылка\"");
                break;
            case STATEMENT_DENIED:
                simpleMailMessage.setSubject("Кредитная заявка");
                simpleMailMessage.setText("Здравствуйте! по вашей заявке на кредит принято отрицательное решение. "
                        + "Повторно подать заявление возможно не ранее, чем через 60 дней.");
                break;
            case CREATE_DOCUMENTS:
                simpleMailMessage.setSubject("Одобренная кредитная заявка");
                simpleMailMessage.setText("Здравствуйте! Для оформления документов, просьба перейти по ссылке: "
                        + "\"ссылка\"");
                break;
            case CREDIT_ISSUED:
                simpleMailMessage.setSubject("Кредит выдан");
                simpleMailMessage.setText("Здравствуйте! кредит №" + emailMessage.getStatementId() + " успешно зачислен на ваш счет.");
                break;
            default:
                throw new IllegalArgumentException("Некорректная тема.");
        }

        return simpleMailMessage;
    }

    public static SimpleMailMessage getMessageSendSesCode(EmailMessage emailMessage, String sesCode) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailMessage.getAddress());
        simpleMailMessage.setSubject("Подписание документов");
        simpleMailMessage.setText("Здравствуйте! для подписания документов, просьба перейти по ссылке: "
                + "\"ссылка\" \n" + "Ваш код: " + sesCode);
        return simpleMailMessage;
    }
}
