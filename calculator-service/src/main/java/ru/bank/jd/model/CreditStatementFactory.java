package ru.bank.jd.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bank.jd.configuration.AppProperties;
import ru.bank.jd.model.dto.CreditDto;
import ru.bank.jd.model.dto.ScoringDataDto;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
@AllArgsConstructor
public class CreditStatementFactory {
    private final AppProperties appProperties;

    public CreditDto fillCreditForm(ScoringDataDto scoringDataDto) {
        CreditDto creditDto = new CreditDto();
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
