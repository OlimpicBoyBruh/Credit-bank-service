package ru.bank.jd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bank.jd.component.RequestDealServiceRest;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.StatementDto;
import ru.bank.jd.dto.enumerated.Theme;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaListenersTest {
    @Mock
    private RequestDealServiceRest requestDealServiceRest;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private KafkaListeners kafkaListeners;

    @Test
    void sendEmailMessageSuccess() {
        assertDoesNotThrow(() -> kafkaListeners.sendEmailMessage(getEmailMessage()));
        verify(emailService, times(1)).sendSimpleEmail(any());
    }

    @Test
    void sendEmailMessageException() {
        doThrow(new RuntimeException("Error Test")).when(emailService).sendSimpleEmail(any(EmailMessage.class));
        assertThrows(RuntimeException.class, () -> kafkaListeners.sendEmailMessage(getEmailMessage()));
    }

    @Test
    void finishRegistrationSuccess() {
        assertDoesNotThrow(() -> kafkaListeners.finishRegistration(getEmailMessage()));
        verify(emailService, times(1)).sendMimeMessageDocument(any(), any());
    }

    @Test
    void finishRegistrationException() {
        doThrow(new RuntimeException("Error Test")).when(emailService).sendMimeMessageDocument(any(), any());
        assertThrows(RuntimeException.class, () -> kafkaListeners.finishRegistration(getEmailMessage()));
    }
    @Test
    void sendSesCodeSuccess() {
        when(requestDealServiceRest.getStatementDto(any())).thenReturn(getStatementDto());
        assertDoesNotThrow(() -> kafkaListeners.sendSesCode(getEmailMessage()));
        verify(emailService, times(1)).sendSesCode(any(), any());
    }
    @Test
    void sendSesCodeException() {
        when(requestDealServiceRest.getStatementDto(any())).thenReturn(getStatementDto());
        doThrow(new RuntimeException("Error Test")).when(emailService).sendSesCode(any(), any());
        assertThrows(RuntimeException.class, () -> kafkaListeners.sendSesCode(getEmailMessage()));
    }

    private EmailMessage getEmailMessage() {
        return new EmailMessage("testId", "test@mail.ru", Theme.STATEMENT_DENIED);
    }

    private StatementDto getStatementDto() {
        return StatementDto.builder()
                .statementId(UUID.randomUUID())
                .firstName("John")
                .middleName("Doe")
                .sesCode("12345")
                .lastName("Smith")
                .birthdate(LocalDate.of(1990, 5, 15))
                .creditId(UUID.randomUUID())
                .amount(new BigDecimal("10000.00"))
                .term(12)
                .monthlyPayment(new BigDecimal("850.00"))
                .rate(new BigDecimal("0.05"))
                .psk(new BigDecimal("100.00"))
                .isInsuranceEnabled(true)
                .build();
    }
}
