package ru.bank.jd.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.enumerated.Theme;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailMessageFactory {
    public static SimpleMailMessage getSimpleMessage(EmailMessage emailMessage) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailMessage.getAddress());

        if (emailMessage.getTheme().equals(Theme.FINISH_REGISTRATION)) {
            simpleMailMessage.setSubject("Завершение оформления заявления");
            simpleMailMessage.setText("Здравствуйте! Для завершения оформления вашей заявки," +
                    " просим заполнить анкету по ссылке: " + "\"ссылка\"");
        } else if (emailMessage.getTheme().equals(Theme.STATEMENT_DENIED)) {
            simpleMailMessage.setSubject("Кредитная заявка");
            simpleMailMessage.setText("Здравствуйте! по вашей заявке на кредит принято отрицательное решение. "
                    + "Повторно подать заявление возможно не ранее, чем через 60 дней.");
        } else if (emailMessage.getTheme().equals(Theme.CREATE_DOCUMENTS)) {
            simpleMailMessage.setSubject("Одобренная кредитная заявка");
            simpleMailMessage.setText("Здравствуйте! Для оформления документов, просьба перейти по ссылке: "
                    + "\"ссылка\"");

        } else if (emailMessage.getTheme().equals(Theme.CREDIT_ISSUED)) {
            simpleMailMessage.setSubject("Кредит выдан");
            simpleMailMessage.setText("Здравствуйте! кредит №" + emailMessage.getStatementId() + " успешно зачислен на ваш счет.");
        } else {
            throw new IllegalArgumentException("Некорректная тема.");
        }
        return simpleMailMessage;
    }

    public static SimpleMailMessage getMessageSendSesCode(EmailMessage emailMessage, String sesCode) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailMessage.getAddress());
        simpleMailMessage.setSubject("Подписание документов");
        simpleMailMessage.setText("Здравствуйте! дял подписания документов, просьба перейти по ссылке: "
                + "\"ссылка\" \n" + "Ваш код: " + sesCode);
        return simpleMailMessage;
    }
}
