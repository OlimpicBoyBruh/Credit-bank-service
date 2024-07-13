package ru.bank.jd.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Кредитное предложение.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(description = "Информация о предложении кредита")
public class LoanOfferDto {
    /**
     * Идентификатор заявления.
     */
    @NotBlank(message = "Идентификатор заявки не может быть пустым или null")
    @Schema(description = "Идентификатор заявки", defaultValue = "dfc3a2f9-20b4-45b8-a501-ba9ba229ec42")
    private String statementId;
    /**
     * Запрашиваемая сумма.
     */
    @NotNull(message = "Запрошенная сумма не может быть пустой или равен null")
    @Schema(description = "Запрошенная сумма", defaultValue = "1000000")
    private BigDecimal requestedAmount;
    /**
     * Общая стоимость кредита.
     */
    @NotNull(message = "Общая сумма не может быть пустой или равен null")
    @Schema(description = "Общая сумма", defaultValue = "1150944.72")
    private BigDecimal totalAmount;
    /**
     * Срок.
     */
    @NotNull(message = "Срок не может быть пустым или равен null")
    @Schema(description = "Срок кредита в месяцах", defaultValue = "12")
    private Integer term;
    /**
     * Ежемесячный платеж.
     */
    @NotNull(message = "Ежемесячный платёж не может быть пустым или равен null")
    @Schema(description = "Ежемесячный платеж", defaultValue = "95912.06")
    private BigDecimal monthlyPayment;
    /**
     * Процентная ставка.
     */
    @NotNull(message = "Процентная ставка не может быть пустой или равен null")
    @Schema(description = "Процентная ставка", defaultValue = "17.35")
    private BigDecimal rate;
    /**
     * Включена ли страховка.
     */
    @NotNull(message = "isInsuranceEnabled не может быть пустым или равен null")
    @Schema(description = "Включена ли страховка", defaultValue = "true")
    private Boolean isInsuranceEnabled;
    /**
     * Является ли зарплатным клиентом.
     */
    @NotNull(message = "isSalaryClient не может быть пустым или равен null")
    @Schema(description = "Является ли клиентом с заработной платой", defaultValue = "true")
    private Boolean isSalaryClient;
}
