package ru.bank.jd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoanOfferDto {
    /**
     * Идентификатор заявления.
     */
    private String statementId;
    /**
     * Запрашиваемая сумма.
     */
    private BigDecimal requestedAmount;
    /**
     * Общая стоимость кредита.
     */
    private BigDecimal totalAmount;
    /**
     * Срок.
     */
    private Integer term;
    /**
     * Ежемесячный платеж.
     */
    private BigDecimal monthlyPayment;
    /**
     * Процентная ставка.
     */
    private BigDecimal rate;
    /**
     * Включена ли страховка.
     */
    private Boolean isInsuranceEnabled;
    /**
     * Является ли зарплатным клиентом.
     */
    private Boolean isSalaryClient;

    @Override
    public String toString() {
        return "LoanOfferDto{" +
                "statementId='" + statementId + '\'' +
                ", requestedAmount=" + requestedAmount +
                ", totalAmount=" + totalAmount +
                ", term=" + term +
                ", monthlyPayment=" + monthlyPayment +
                ", rate=" + rate +
                ", isInsuranceEnabled=" + isInsuranceEnabled +
                ", isSalaryClient=" + isSalaryClient +
                '}';
    }
}
