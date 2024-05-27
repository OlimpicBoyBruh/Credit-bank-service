package ru.bank.jd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.bank.jd.model.dto.PaymentScheduleElementDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
public class PaymentScheduleGenerator {
    private BigDecimal amount;
    private BigDecimal interestRate;
    private Integer term;

    public List<PaymentScheduleElementDto> generatePaymentSchedule() {
        log.info("The process of forming a payment schedule begins.");
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        BigDecimal remainingDebt = amount;
        BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.DOWN);
        BigDecimal monthlyPayment = calculateMonthlyPayment();
        for (int i = 1; i <= term; i++) {
            PaymentScheduleElementDto scheduleElement = new PaymentScheduleElementDto();
            scheduleElement.setNumber(i);
            scheduleElement.setDate(LocalDate.now().plusMonths(i));
            scheduleElement.setTotalPayment(monthlyPayment);
            scheduleElement.setInterestPayment(remainingDebt.multiply(monthlyInterestRate)
                    .setScale(2, RoundingMode.DOWN));

            if (i == term) {
                scheduleElement.setDebtPayment(remainingDebt);
                scheduleElement.setRemainingDebt(BigDecimal.ZERO);
            } else {
                scheduleElement.setDebtPayment(monthlyPayment.subtract(scheduleElement.getInterestPayment()));
                scheduleElement.setRemainingDebt(remainingDebt.subtract(scheduleElement.getDebtPayment())
                        .setScale(2, RoundingMode.DOWN));
            }

            remainingDebt = scheduleElement.getRemainingDebt();
            paymentSchedule.add(scheduleElement);
        }
        log.info("The schedule has been successfully formed.");
        return paymentSchedule;
    }

    public BigDecimal calculateMonthlyPayment() {
        BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal factor = monthlyInterestRate.add(BigDecimal.ONE).pow(term);
        BigDecimal numerator = amount.multiply(monthlyInterestRate).multiply(factor);
        BigDecimal denominator = factor.subtract(BigDecimal.ONE);
        log.info("The monthly payment was calculated successfully.");
        return numerator.divide(denominator, 2, RoundingMode.DOWN);
    }
}