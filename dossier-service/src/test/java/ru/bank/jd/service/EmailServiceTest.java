package ru.bank.jd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.enumerated.Theme;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private EmailService emailService;

    @Test
    void testSendSimpleEmailSuccess() {
        assertDoesNotThrow(() -> emailService.sendSimpleEmail(getEmailMessage()));
        verify(javaMailSender, times(1)).send((SimpleMailMessage) any());
    }

    @Test
    void testSendSimpleEmailFailure() {
        doThrow(new MailSendException("Error Test")).when(javaMailSender).send(any(SimpleMailMessage.class));
        assertThrows(MailException.class, () -> emailService.sendSimpleEmail(getEmailMessage()));
    }

    @Test
    void testSendSesCodeSuccess() {
        assertDoesNotThrow(() -> emailService.sendSesCode(getEmailMessage(), "SesCodeTest"));
        verify(javaMailSender, times(1)).send((SimpleMailMessage) any());
    }

    @Test
    void testSendSesCodeFailure() {
        doThrow(new MailSendException("Error Test")).when(javaMailSender).send(any(SimpleMailMessage.class));
        assertThrows(MailException.class, () -> emailService.sendSesCode(getEmailMessage(), "SesCodeTest"));
    }

    private EmailMessage getEmailMessage() {
        return new EmailMessage("testId", "test@mail.ru", Theme.STATEMENT_DENIED);
    }

}