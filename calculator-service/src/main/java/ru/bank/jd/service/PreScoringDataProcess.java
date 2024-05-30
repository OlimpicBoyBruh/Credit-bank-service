package ru.bank.jd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bank.jd.dto.LoanOfferDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PreScoringDataProcess {
    private final PaymentScheduleGenerator paymentScheduleGenerator;
    private static final BigDecimal RATE_MULTIPLICATION = new BigDecimal("100");

    public LoanOfferDto createOffer(boolean isSalaryClient, boolean isInsuranceEnabled,
                                    double interestRate, double insuranceRate,
                                    int term, BigDecimal amount) {
        BigDecimal totalAmount = isInsuranceEnabled ? amount
                .multiply(BigDecimal.valueOf(1 + insuranceRate / 100)).setScale(2, RoundingMode.DOWN) : amount;

        BigDecimal finalInterestRate =
                calculateFinalInterestRate(interestRate, isSalaryClient, isInsuranceEnabled).divide(RATE_MULTIPLICATION);
        BigDecimal monthlyPayment = paymentScheduleGenerator
                .calculateMonthlyPayment(finalInterestRate, term, totalAmount);
        return new LoanOfferDto(UUID.randomUUID().toString(), amount,
                monthlyPayment.multiply(new BigDecimal(term)), term, monthlyPayment,
                calculateFinalInterestRate(interestRate, isSalaryClient, isInsuranceEnabled),
                isInsuranceEnabled, isSalaryClient);
    }

    public BigDecimal calculateFinalInterestRate(double basicInterestRate,
                                                 boolean isSalaryClient, boolean isInsuranceEnabled) {
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
