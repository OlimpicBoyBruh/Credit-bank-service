package ru.bank.jd.junit.mapping;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.bank.jd.dto.PaymentScheduleElementDto;
import ru.bank.jd.dto.api.StatementDto;
import ru.bank.jd.dto.enumerated.ApplicationStatus;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.entity.Client;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.entity.Statement;
import ru.bank.jd.mapping.StatementMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatementMapperTest {
    private final StatementMapper statementMapper = Mappers.getMapper(StatementMapper.class);
    private static final UUID ID = UUID.fromString("12142ab4-d0dd-4853-b866-293b11799303");
    private static final String FIRST_NAME = "John";
    private static final String MIDDLE_NAME = "Stanislavovich";
    private static final String LAST_NAME = "Doe";
    private static final String SES_CODE = "ABC123";
    private static final LocalDate BIRTHDATE = LocalDate.of(1980, 5, 15);
    private static final BigDecimal AMOUNT = new BigDecimal("100000");
    private static final int TERM = 12;
    private static final BigDecimal MONTHLY_PAYMENT = new BigDecimal("9559");
    private static final BigDecimal RATE = new BigDecimal("12.5");
    private static final BigDecimal PSK = new BigDecimal("115000");

    @Test
    void setStatementMapperTest() {
        StatementDto statementDto = statementMapper.statementToStatementDto(getStatement());
        assertAll(
                () -> assertEquals(ID, statementDto.getStatementId()),
                () -> assertEquals(FIRST_NAME, statementDto.getFirstName()),
                () -> assertEquals(MIDDLE_NAME, statementDto.getMiddleName()),
                () -> assertEquals(LAST_NAME, statementDto.getLastName()),
                () -> assertEquals(BIRTHDATE, statementDto.getBirthdate()),
                () -> assertEquals(ID, statementDto.getCreditId()),
                () -> assertEquals(SES_CODE, statementDto.getSesCode()),
                () -> assertEquals(AMOUNT, statementDto.getAmount()),
                () -> assertEquals(TERM, statementDto.getTerm()),
                () -> assertEquals(MONTHLY_PAYMENT, statementDto.getMonthlyPayment()),
                () -> assertEquals(RATE, statementDto.getRate()),
                () -> assertEquals(PSK, statementDto.getPsk())
        );
    }

    private Client getClient() {
        return Client.builder()
                .clientId(ID)
                .firstName("John")
                .lastName("Doe")
                .middleName("Stanislavovich")
                .birthdate(LocalDate.of(1980, 5, 15))
                .email("john.doe@example.com")
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(0)
                .accountNumber("1234567890")
                .build();
    }

    private Credit getCredit() {
        return Credit.builder()
                .creditId(ID)
                .amount(new BigDecimal("100000"))
                .term(12)
                .monthlyPayment(new BigDecimal("9559"))
                .rate(new BigDecimal("12.5"))
                .psk(new BigDecimal("115000"))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .paymentSchedule(List.of(new PaymentScheduleElementDto()))
                .build();
    }

    private Statement getStatement() {
        return Statement.builder()
                .statementId(ID)
                .client(getClient())
                .creditId(getCredit())
                .status(ApplicationStatus.APPROVED.toString())
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .sesCode("ABC123")
                .appliedOffer(null)
                .statusHistory(new ArrayList<>())
                .build();
    }
}