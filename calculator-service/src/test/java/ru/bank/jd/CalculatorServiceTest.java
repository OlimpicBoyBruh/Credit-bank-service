package ru.bank.jd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bank.jd.model.CreditOfferFactory;
import ru.bank.jd.model.CreditStatementFactory;
import ru.bank.jd.model.dto.*;
import ru.bank.jd.model.statement.Gender;
import ru.bank.jd.model.statement.MaritalStatus;
import ru.bank.jd.service.CalculatorService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {
    @Mock
    private CreditOfferFactory creditOfferFactory;
    @Mock
    private CreditStatementFactory creditStatementFactory;
    @InjectMocks
    private CalculatorService calculatorService;
    private LoanStatementRequestDto loanStatementRequestDto;
    private ScoringDataDto scoringDataDto;
    @BeforeEach
    public void init() {
        loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(new BigDecimal("100000"));
        loanStatementRequestDto.setTerm(12);
        loanStatementRequestDto.setFirstName("Иван");
        loanStatementRequestDto.setLastName("Иванов");
        loanStatementRequestDto.setMiddleName("Иванович");
        loanStatementRequestDto.setEmail("ivanov@example.com");
        loanStatementRequestDto.setBirthdate(LocalDate.of(1990, 5, 15));
        loanStatementRequestDto.setPassportSeries("1234");
        loanStatementRequestDto.setPassportNumber("567890");

        scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(new BigDecimal("100000.50"));
        scoringDataDto.setTerm(12);
        scoringDataDto.setFirstName("Иван");
        scoringDataDto.setLastName("Иванов");
        scoringDataDto.setMiddleName("Иванович");
        scoringDataDto.setGender(Gender.MALE);
        scoringDataDto.setBirthdate(LocalDate.of(1990, 5, 15));
        scoringDataDto.setPassportSeries("1234");
        scoringDataDto.setPassportNumber("567890");
        scoringDataDto.setPassportIssueDate(LocalDate.of(2010, 8, 20));
        scoringDataDto.setPassportIssueBranch("Отделение №1");
        scoringDataDto.setMaritalStatus(MaritalStatus.MARRIED);
        scoringDataDto.setDependentAmount(2);
        scoringDataDto.setEmployment(new EmploymentDto());
        scoringDataDto.setAccountNumber("1234567890123456");
        scoringDataDto.setIsInsuranceEnabled(true);
        scoringDataDto.setIsSalaryClient(true);
    }
    @Test
    public void createOffersTest() {
        when(creditOfferFactory.
                createLoanOffer(true,true,loanStatementRequestDto))
                .thenReturn(new LoanOfferDto());
        List<LoanOfferDto> loanOfferDtoList = calculatorService.generateLoanOffers(loanStatementRequestDto);
        Assertions.assertEquals(loanOfferDtoList.size(), 4);
    }
    @Test
    public void createCreditDtoTest() {
        CreditDto creditDtoMock = new CreditDto();
        creditDtoMock.setTerm(6);
        creditDtoMock.setAmount(BigDecimal.valueOf(50000));
        when(creditStatementFactory.fillCreditForm(scoringDataDto))
                .thenReturn(creditDtoMock);
        CreditDto creditDto = calculatorService.prepareCredit(scoringDataDto);
        Assertions.assertEquals(creditDto.getTerm(), 6);
        Assertions.assertEquals(creditDto.getAmount(), BigDecimal.valueOf(50000));
    }
}
