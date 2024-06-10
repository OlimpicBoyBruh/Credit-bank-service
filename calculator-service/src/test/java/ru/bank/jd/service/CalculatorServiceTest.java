package ru.bank.jd.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bank.jd.component.CreditOfferFactory;
import ru.bank.jd.component.CreditStatementFactory;
import ru.bank.jd.dto.*;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {
    @Mock
    private CreditOfferFactory creditOfferFactory;
    @Mock
    private CreditStatementFactory creditStatementFactory;
    @InjectMocks
    private CalculatorService calculatorService;

    @Test
    void createOffersTest() {
        LoanStatementRequestDto loanStatementRequestDto = getLoanStatementRequestDto();
        when(creditOfferFactory.
                createLoanOffer(true, true, loanStatementRequestDto))
                .thenReturn(new LoanOfferDto("12345", new BigDecimal("10000.00"),
                                new BigDecimal("12000.00"), 12, new BigDecimal("1000.00"),
                                new BigDecimal("10.5"), true, false
                        )
                );
        when(creditOfferFactory.
                createLoanOffer(false, true, loanStatementRequestDto))
                .thenReturn(new LoanOfferDto("12345", new BigDecimal("10000.00"),
                                new BigDecimal("12000.00"), 12, new BigDecimal("1000.00"),
                                new BigDecimal("10.5"), true, false
                        )
                );
        when(creditOfferFactory.
                createLoanOffer(true, false, loanStatementRequestDto))
                .thenReturn(new LoanOfferDto("12345", new BigDecimal("10000.00"),
                                new BigDecimal("12000.00"), 12, new BigDecimal("1000.00"),
                                new BigDecimal("10.5"), true, false
                        )
                );
        when(creditOfferFactory.
                createLoanOffer(false, false, loanStatementRequestDto))
                .thenReturn(new LoanOfferDto("12345", new BigDecimal("10000.00"),
                                new BigDecimal("12000.00"), 12, new BigDecimal("1000.00"),
                                new BigDecimal("10.5"), true, false
                        )
                );

        List<LoanOfferDto> loanOfferDtoList = calculatorService.generateLoanOffers(loanStatementRequestDto);
        Assertions.assertEquals(4, loanOfferDtoList.size());
    }

    @Test
    void createCreditDtoTest() {
        ScoringDataDto scoringDataDto = getScoringDataDto();
        CreditDto creditDtoMock = getCreditDto();

        when(creditStatementFactory.fillCreditForm(scoringDataDto))
                .thenReturn(creditDtoMock);

        CreditDto creditDto = calculatorService.prepareCredit(scoringDataDto);
        assertAll(() -> Assertions.assertEquals(12, creditDto.getTerm()),
                () -> Assertions.assertEquals(creditDto.getAmount(), new BigDecimal("100000.50")));
    }

    private LoanStatementRequestDto getLoanStatementRequestDto() {
        return LoanStatementRequestDto.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .firstName("Иван")
                .lastName("Иванов")
                .middleName("Иванович")
                .email("ivanov@example.com")
                .birthdate(LocalDate.of(1990, 5, 15))
                .passportSeries("1234")
                .passportNumber("567890")
                .build();
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
                .employment(new EmploymentDto())
                .accountNumber("1234567890123456")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
    }

    private CreditDto getCreditDto() {
        return CreditDto.builder()
                .amount(new BigDecimal("100000.50"))
                .term(12)
                .monthlyPayment(new BigDecimal("9000.00"))
                .rate(new BigDecimal("10.5"))
                .psk(new BigDecimal("108000.00"))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .paymentSchedule(new ArrayList<>())
                .build();
    }
}