package ru.bank.jd.util;

import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.enumerated.Theme;
import static org.junit.jupiter.api.Assertions.*;

class EmailMessageFactoryTest {

    @Test
    void getSimpleMessageFinishRegistration() {
        EmailMessage emailMessage = getEmailMessage();
        emailMessage.setTheme(Theme.FINISH_REGISTRATION);
        SimpleMailMessage simpleMailMessage = EmailMessageFactory.getSimpleMessage(emailMessage);
        assertAll(
                () -> assertEquals("Завершение оформления заявления", simpleMailMessage.getSubject()),
                () -> assertEquals("test@mail.ru", String.join("", simpleMailMessage.getTo()))
        );
    }

    @Test
    void getSimpleMessageStatementDenied() {
        EmailMessage emailMessage = getEmailMessage();
        SimpleMailMessage simpleMailMessage = EmailMessageFactory.getSimpleMessage(emailMessage);
        assertAll(
                () -> assertEquals("Кредитная заявка", simpleMailMessage.getSubject()),
                () -> assertEquals("test@mail.ru", String.join("", simpleMailMessage.getTo()))
        );
    }

    @Test
    void getSimpleMessageCreateDocument() {
        EmailMessage emailMessage = getEmailMessage();
        emailMessage.setTheme(Theme.CREATE_DOCUMENTS);
        SimpleMailMessage simpleMailMessage = EmailMessageFactory.getSimpleMessage(emailMessage);
        assertAll(
                () -> assertEquals("Одобренная кредитная заявка", simpleMailMessage.getSubject()),
                () -> assertEquals("test@mail.ru", String.join("", simpleMailMessage.getTo()))
        );
    }

    @Test
    void getSimpleMessageCreditIssued() {
        EmailMessage emailMessage = getEmailMessage();
        emailMessage.setTheme(Theme.CREDIT_ISSUED);
        SimpleMailMessage simpleMailMessage = EmailMessageFactory.getSimpleMessage(emailMessage);
        assertAll(
                () -> assertEquals("Кредит выдан", simpleMailMessage.getSubject()),
                () -> assertEquals("test@mail.ru", String.join("", simpleMailMessage.getTo()))
        );
    }

    @Test
    void getSimpleMessageException() {
        EmailMessage emailMessage = getEmailMessage();
        emailMessage.setTheme(Theme.SEND_SES);
        assertThrows(IllegalArgumentException.class, () -> EmailMessageFactory.getSimpleMessage(emailMessage));
    }

    @Test
    void getMessageSendSesCode() {
        EmailMessage emailMessage = getEmailMessage();
        SimpleMailMessage simpleMailMessage = EmailMessageFactory.getMessageSendSesCode(getEmailMessage(), "testSesCode");
        assertAll(
                () -> assertEquals("test@mail.ru", String.join("", simpleMailMessage.getTo())),
                () -> assertEquals("Подписание документов", simpleMailMessage.getSubject())
        );


    }

    private EmailMessage getEmailMessage() {
        return new EmailMessage("testId", "test@mail.ru", Theme.STATEMENT_DENIED);
    }
}