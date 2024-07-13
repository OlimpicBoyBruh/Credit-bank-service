package ru.bank.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDto {
    /**
     * Сумма.
     */
    private BigDecimal amount;
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
     * Полная стоимость кредита.
     */
    private BigDecimal psk;
    /**
     * Включена ли страховка.
     */
    private Boolean isInsuranceEnabled;
    /**
     * Является зарплатным клиентом.
     */
    private Boolean isSalaryClient;
    /**
     * График платежей.
     */
    private List<PaymentScheduleElementDto> paymentSchedule;
}
