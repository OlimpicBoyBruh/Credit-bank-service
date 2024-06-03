package ru.bank.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Payment number.")
    private Integer number;
    /**
     * Дата платежа.
     */
    @Schema(description = "Payment date.")
    private LocalDate date;
    /**
     * Полный платёж.
     */
    @Schema(description = "Total Payment.")
    private BigDecimal totalPayment;
    /**
     * Проценты от платежа.
     */
    @Schema(description = "Interest payment.")
    private BigDecimal interestPayment;
    /**
     * Выплата долга.
     */
    @Schema(description = "Debt Payment.")
    private BigDecimal debtPayment;
    /**
     * Оставшийся долг.
     */
    @Schema(description = "The remaining loan debt.")
    private BigDecimal remainingDebt;
}
