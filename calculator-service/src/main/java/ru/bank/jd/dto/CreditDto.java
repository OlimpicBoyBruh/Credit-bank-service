package ru.bank.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class CreditDto {
    /**
     * Сумма.
     */
    @Schema(description = "Amount.")
    private BigDecimal amount;
    /**
     * Срок.
     */
    @Schema(description = "Term.")
    private Integer term;
    /**
     * Ежемесячный платеж.
     */
    @Schema(description = "Monthly loan payment.")
    private BigDecimal monthlyPayment;
    /**
     * Процентная ставка.
     */
    @Schema(description = "Interest rate.")
    private BigDecimal rate;
    /**
     * Полная стоимость кредита.
     */
    @Schema(description = "The full cost of the loan.")
    private BigDecimal psk;
    /**
     * Включена ли страховка.
     */
    @Schema(description = "Is insurance enabled.")
    private Boolean isInsuranceEnabled;
    /**
     * Является зарплатным клиентом.
     */
    @Schema(description = "Is salary client.")
    private Boolean isSalaryClient;
    /**
     * График платежей.
     */
    @Schema(description = "Credit payment schedule.")
    private List<PaymentScheduleElementDto> paymentSchedule;
}
