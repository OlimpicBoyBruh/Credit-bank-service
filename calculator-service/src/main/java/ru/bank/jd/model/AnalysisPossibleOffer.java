package ru.bank.jd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.bank.api.dto.LoanOfferDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class AnalysisPossibleOffer {
    private final boolean isSalaryClient;
    private final boolean isInsuranceEnabled;
    private final double basicInterestRate;
    private final double insuranceRate;
    private final int term;
    private final BigDecimal requestedAmount;

    public LoanOfferDto createOffer() {
        double finalInterestRate = calculateFinalInterestRate();
        BigDecimal monthlyPayment = calculateMonthlyPayment(finalInterestRate);
        LoanOfferDto loanOfferDto = new LoanOfferDto();
        loanOfferDto.setStatementId(UUID.randomUUID().toString());
        loanOfferDto.setRequestedAmount(requestedAmount);
        loanOfferDto.setTerm(term);
        loanOfferDto.setRate(BigDecimal.valueOf(finalInterestRate));
        loanOfferDto.setIsSalaryClient(isSalaryClient);
        loanOfferDto.setIsInsuranceEnabled(isInsuranceEnabled);
        loanOfferDto.setMonthlyPayment(calculateMonthlyPayment(finalInterestRate));
        loanOfferDto.setTotalAmount(loanOfferDto.getMonthlyPayment().multiply(BigDecimal.valueOf(term)));
        return new LoanOfferDto(UUID.randomUUID().toString(), requestedAmount, loanOfferDto.getMonthlyPayment().multiply(BigDecimal.valueOf(term)),
                term, monthlyPayment, BigDecimal.valueOf(calculateFinalInterestRate()),
                isInsuranceEnabled, isSalaryClient);
    }

    private double calculateFinalInterestRate() {
        double preInterestRate = basicInterestRate;
        if (isSalaryClient) {
            preInterestRate -= 1.2;
        }
        if (isInsuranceEnabled) {
            preInterestRate -= 1.45;
        }
        return preInterestRate;
    }

    private BigDecimal calculateMonthlyPayment(double interestRate) {
        double monthlyInterestRate = (interestRate / 100) / 12;
        double totalAmountMultiplier = isInsuranceEnabled ? 1.0495 : 1.0;
        return BigDecimal.valueOf((requestedAmount.doubleValue() * totalAmountMultiplier)
                * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, term) /
                (Math.pow(1 + monthlyInterestRate, term) - 1)).setScale(2, RoundingMode.CEILING);
    }
}
