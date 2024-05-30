package ru.bank.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.dto.PaymentScheduleElementDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс рассчитывает полный график платежей и ежемесячный платёж.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentScheduleGenerator {
    private static final BigDecimal twelve = new BigDecimal("12");

    /**
     * Составляет полный кредитный график платежей.
     *
     * @param amount       сумма кредита.
     * @param interestRate процентная ставка.
     * @param term         срок кредита.
     * @return график платежей.
     */
    public List<PaymentScheduleElementDto> generatePaymentSchedule(BigDecimal amount,
                                                                   BigDecimal interestRate, Integer term) {
        log.info("The process of forming a payment schedule begins.");
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        BigDecimal remainingDebt = amount;
        BigDecimal monthlyInterestRate = interestRate.divide(twelve, 10, RoundingMode.DOWN);
        BigDecimal monthlyPayment = calculateMonthlyPayment(interestRate, term, amount);

        for (int numberPayment = 1; numberPayment <= term; numberPayment++) {
            PaymentScheduleElementDto.PaymentScheduleElementDtoBuilder builder = PaymentScheduleElementDto.builder()
                    .number(numberPayment)
                    .date(LocalDate.now().plusMonths(numberPayment))
                    .totalPayment(monthlyPayment)
                    .interestPayment(remainingDebt.multiply(monthlyInterestRate).setScale(2, RoundingMode.DOWN));

            PaymentScheduleElementDto scheduleElement = builder.build();

            if (numberPayment == term) {
                scheduleElement = builder
                        .debtPayment(remainingDebt)
                        .remainingDebt(BigDecimal.ZERO)
                        .build();
            } else {
                BigDecimal calculatedDebtPayment = monthlyPayment.subtract(scheduleElement.getInterestPayment());
                BigDecimal calculatedRemainingDebt = remainingDebt.subtract(calculatedDebtPayment)
                        .setScale(2, RoundingMode.DOWN);

                scheduleElement = builder
                        .debtPayment(calculatedDebtPayment)
                        .remainingDebt(calculatedRemainingDebt)
                        .build();
            }

            remainingDebt = scheduleElement.getRemainingDebt();
            paymentSchedule.add(scheduleElement);
        }

        log.info("The schedule has been successfully formed.");
        return paymentSchedule;
    }

    /**
     * Рассчитывает ежемесячный платёж по кредиту.
     *
     * @param interestRate процентная ставка.
     * @param term         срок кредита.
     * @param amount       сумма кредита.
     * @return ежемесячный платёж.
     */
    public BigDecimal calculateMonthlyPayment(BigDecimal interestRate, Integer term, BigDecimal amount) {
        BigDecimal monthlyInterestRate = interestRate.divide(twelve, 10, RoundingMode.HALF_UP);
        BigDecimal factor = monthlyInterestRate.add(BigDecimal.ONE).pow(term);
        BigDecimal numerator = amount.multiply(monthlyInterestRate).multiply(factor);
        BigDecimal denominator = factor.subtract(BigDecimal.ONE);

        log.info("The monthly payment was calculated successfully.");
        return numerator.divide(denominator, 2, RoundingMode.DOWN);
    }
}
