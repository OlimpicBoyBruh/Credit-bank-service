package ru.bank.jd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.bank.jd.model.dto.LoanOfferDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class PreScoringDataProcess {
    private final boolean isSalaryClient;
    private final boolean isInsuranceEnabled;
    private final double basicInterestRate;
    private final double insuranceRate;
    private final int term;
    private final BigDecimal requestedAmount;

    public LoanOfferDto createOffer() {
        BigDecimal totalAmount = isInsuranceEnabled ? requestedAmount
                .multiply(BigDecimal.valueOf(1 + insuranceRate / 100)) : requestedAmount;
        BigDecimal finalInterestRate = calculateFinalInterestRate().divide(BigDecimal.valueOf(100));
        PaymentScheduleGenerator paymentScheduleGenerator =
                new PaymentScheduleGenerator(totalAmount, finalInterestRate, term);
        BigDecimal monthlyPayment = paymentScheduleGenerator
                .calculateMonthlyPayment();
        return new LoanOfferDto(UUID.randomUUID().toString(), requestedAmount,
                monthlyPayment.multiply(BigDecimal.valueOf(term)),
                term, monthlyPayment, calculateFinalInterestRate(),
                isInsuranceEnabled, isSalaryClient);
    }

    public BigDecimal calculateFinalInterestRate() {
        double preInterestRate = basicInterestRate;
        if (isSalaryClient) {
            preInterestRate -= 1.2;
        }
        if (isInsuranceEnabled) {
            preInterestRate -= 1.45;
        }
        return BigDecimal.valueOf(preInterestRate).setScale(2, RoundingMode.DOWN);
    }
}
