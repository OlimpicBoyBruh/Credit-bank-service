package ru.bank.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentScheduleElementDto {
    /**
     * Номер платежа.
     */
    private Integer number;
    /**
     * Дата платежа.
     */
    private LocalDate date;
    /**
     * Полный платёж.
     */
    private BigDecimal totalPayment;
    /**
     * Проценты от платежа.
     */
    private BigDecimal interestPayment;
    /**
     * Выплата долга.
     */
    private BigDecimal debtPayment;
    /**
     * Оставшийся долг.
     */
    private BigDecimal remainingDebt;
}
