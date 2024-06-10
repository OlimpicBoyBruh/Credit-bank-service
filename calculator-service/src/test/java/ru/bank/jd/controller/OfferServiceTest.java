package ru.bank.jd.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.bank.jd.dto.EmploymentDto;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.dto.enumerated.Position;
import ru.bank.jd.exception.CreditDeniedException;
import ru.bank.jd.service.ScoringDataProcess;
import java.math.BigDecimal;
import java.time.LocalDate;

class OfferServiceTest {


    @Test
    void getInterestRateSuccessfullyTest() {
        ScoringDataDto scoringDataDto = getScoringDataDto();
        BigDecimal interestRate = ScoringDataProcess.getInterestRate(scoringDataDto, 20.0);
        Assertions.assertEquals(interestRate, new BigDecimal("0.1395"));
    }

    @Test
    void getInterestRateUnsuccessfullyTest() {
        ScoringDataDto scoringDataDto = getScoringDataDto();
        scoringDataDto.setBirthdate(LocalDate.now());
        Assertions.assertThrows(CreditDeniedException.class,
                () -> ScoringDataProcess.getInterestRate(scoringDataDto, 20.0));
    }

    private ScoringDataDto getScoringDataDto() {
        return ScoringDataDto.builder()
                .amount(new BigDecimal("100000.50"))
                .term(12)
                .firstName("Иван")
                .lastName("Иванов")
                .middleName("Иванович")
                .gender(Gender.MALE)
                .birthdate(LocalDate.of(1990, 5, 15))
                .passportSeries("1234")
                .passportNumber("567890")
                .passportIssueDate(LocalDate.of(2010, 8, 20))
                .passportIssueBranch("Отделение №1")
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .employment(getEmploymentDto())
                .accountNumber("1234567890123456")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
    }

    private EmploymentDto getEmploymentDto() {
        return new EmploymentDto(EmploymentStatus.EMPLOYED,
                "1234567890", new BigDecimal("50000.00"),
                Position.EMPLOYEE, 18, 6);

    }

}