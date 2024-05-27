package ru.bank.jd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bank.jd.exception.CreditDeniedException;
import ru.bank.jd.model.ScoringDataProcess;
import ru.bank.jd.model.dto.EmploymentDto;
import ru.bank.jd.model.dto.ScoringDataDto;
import ru.bank.jd.model.statement.EmploymentStatus;
import ru.bank.jd.model.statement.Gender;
import ru.bank.jd.model.statement.MaritalStatus;
import ru.bank.jd.model.statement.Position;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ScoringDataProcessTest {
    private ScoringDataDto scoringDataDto;
    private ScoringDataProcess scoringDataProcess;

    @BeforeEach
    public void init() {
        scoringDataDto = new ScoringDataDto();
        scoringDataProcess = new ScoringDataProcess();
        scoringDataDto.setBirthdate(LocalDate.of(2000, 3, 3));
        scoringDataDto.setAmount(new BigDecimal("100000"));
        scoringDataDto.setTerm(6);
        scoringDataDto.setGender(Gender.FEMALE);
        scoringDataDto.setFirstName("Иван");
        scoringDataDto.setLastName("Иванов");
        scoringDataDto.setMiddleName("Иванович");
        scoringDataDto.setAccountNumber("1234567890");
        scoringDataDto.setIsInsuranceEnabled(true);
        scoringDataDto.setIsSalaryClient(true);
        scoringDataDto.setPassportSeries("0123");
        scoringDataDto.setPassportNumber("456789");
        EmploymentDto employmentDto =
                new EmploymentDto(EmploymentStatus.EMPLOYED,"1", new BigDecimal("50000"),
                        Position.MANAGER,18, 6);
        scoringDataDto.setEmployment(employmentDto);
        scoringDataDto.setMaritalStatus(MaritalStatus.MARRIED);
    }

    @Test
    public void getInterestRateSuccessfullyTest() {
       BigDecimal interestRate =  scoringDataProcess.getInterestRate(scoringDataDto, 20.0);
        System.out.println(interestRate);
        Assertions.assertEquals(interestRate, new BigDecimal("0.1671"));
    }
    @Test
    public void getInterestRateUnsuccessfullyTest() {
        scoringDataDto.setBirthdate(LocalDate.now());
        Assertions.assertThrows(CreditDeniedException.class,
                () -> scoringDataProcess.getInterestRate(scoringDataDto, 20.0));
    }
}
