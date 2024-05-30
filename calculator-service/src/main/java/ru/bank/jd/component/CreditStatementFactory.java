package ru.bank.jd.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bank.jd.configuration.AppProperties;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.service.PaymentScheduleGenerator;
import ru.bank.jd.service.ScoringDataProcess;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreditStatementFactory {
    private final AppProperties appProperties;
    private final PaymentScheduleGenerator paymentScheduleGenerator;
    private static final BigDecimal hundred = new BigDecimal(100);

    /**
     * Create credit offer.
     *
     * @param scoringDataDto full data analysis.
     * @return final loan offer.
     */

    public CreditDto fillCreditForm(ScoringDataDto scoringDataDto) {
        log.info("Filling out the CreditDto.");
        BigDecimal totalAmount = Boolean.TRUE.equals(scoringDataDto.getIsInsuranceEnabled()) ? scoringDataDto.getAmount()
                .multiply(BigDecimal.valueOf(1 + appProperties.getInsuranceRate() / 100)) : scoringDataDto.getAmount();

        BigDecimal interestRate = ScoringDataProcess.getInterestRate(scoringDataDto, appProperties.getInterestRate());

        BigDecimal monthlyPayment = paymentScheduleGenerator.calculateMonthlyPayment(interestRate,
                scoringDataDto.getTerm(), totalAmount);
        return CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .rate(interestRate.multiply(hundred).setScale(2, RoundingMode.DOWN))
                .term(scoringDataDto.getTerm())
                .monthlyPayment(monthlyPayment)
                .paymentSchedule(paymentScheduleGenerator.generatePaymentSchedule(totalAmount,
                        interestRate, scoringDataDto.getTerm()))
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .psk(monthlyPayment.multiply(BigDecimal.valueOf(scoringDataDto.getTerm())))
                .build();
    }
}