package ru.bank.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class LoanOfferDto {
    /**
     * Идентификатор заявления.
     */
    @Schema(description = "Statement id.")
    private String statementId;
    /**
     * Запрашиваемая сумма.
     */
    @Schema(description = "Request amount.")
    private BigDecimal requestedAmount;
    /**
     * Общая стоимость кредита.
     */
    @Schema(description = "Total amount.")
    private BigDecimal totalAmount;
    /**
     * Срок.
     */
    @Schema(description = "The term of the loan.")
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
     * Включена ли страховка.
     */
    @Schema(description = "Is insurance Enabled.")
    private Boolean isInsuranceEnabled;
    /**
     * Является ли зарплатным клиентом.
     */
    @Schema(description = "Is salary client.")
    private Boolean isSalaryClient;
}
