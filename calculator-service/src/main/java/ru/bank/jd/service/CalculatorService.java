package ru.bank.jd.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.api.dto.CreditDto;
import ru.bank.api.dto.LoanOfferDto;
import ru.bank.api.dto.LoanStatementRequestDto;
import ru.bank.api.dto.ScoringDataDto;
import ru.bank.jd.configuration.AppProperties;
import ru.bank.jd.model.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CalculatorService {
    private AppProperties appProperties;

    public List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        AnalysisBirthdate.validate(loanStatementRequestDto.getBirthdate());
        log.info("Creating credit offers.");
        return Arrays.asList(
                createLoanOffer(true, true, loanStatementRequestDto),
                createLoanOffer(false, true, loanStatementRequestDto),
                createLoanOffer(true, false, loanStatementRequestDto),
                createLoanOffer(false, false, loanStatementRequestDto)
        );
    }

    public CreditDto prepareCredit(ScoringDataDto scoringDataDto) {
        return fillCreditForm(scoringDataDto);
    }

    private LoanOfferDto createLoanOffer(boolean isSalaryClient, boolean isInsuranceEnabled,
                                         LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Creating credit offer: isSalaryClient - "
                + isSalaryClient + ", isInsuranceEnabled - " + isInsuranceEnabled);
        PreScoringDataProcess preScoringDataProcess = new PreScoringDataProcess(isSalaryClient, isInsuranceEnabled,
                appProperties.getInterestRate(), appProperties.getInsuranceRate(),
                loanStatementRequestDto.getTerm(), loanStatementRequestDto.getAmount());
        return preScoringDataProcess.createOffer();
    }

    private CreditDto fillCreditForm(ScoringDataDto scoringDataDto) {
        log.info("Starting creditDto formation.");
        CreditDto creditDto = new CreditDto();
      AnalysisBirthdate.validate(scoringDataDto.getBirthdate());

        BigDecimal totalAmount = scoringDataDto.getIsInsuranceEnabled() ? scoringDataDto.getAmount()
                .multiply(BigDecimal.valueOf(1 + appProperties.getInsuranceRate() / 100)) : scoringDataDto.getAmount();

        ScoringDataProcess scoringDataProcess = new ScoringDataProcess();
        BigDecimal interestRate = scoringDataProcess.getInterestRate(scoringDataDto, appProperties.getInterestRate());

        PaymentScheduleGenerator paymentScheduleGenerator =
                new PaymentScheduleGenerator(totalAmount, interestRate, scoringDataDto.getTerm());

        creditDto.setAmount(scoringDataDto.getAmount());
        creditDto.setRate(interestRate.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
        creditDto.setTerm(scoringDataDto.getTerm());
        creditDto.setMonthlyPayment(paymentScheduleGenerator.calculateMonthlyPayment());
        creditDto.setPaymentSchedule(paymentScheduleGenerator.generatePaymentSchedule());
        creditDto.setIsSalaryClient(scoringDataDto.getIsSalaryClient());
        creditDto.setIsInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled());
        creditDto.setPsk(paymentScheduleGenerator.calculateMonthlyPayment()
                .multiply(BigDecimal.valueOf(scoringDataDto.getTerm())));

        log.info("CreditDto formation has been successfully completed.");
        return creditDto;
    }
}
