package ru.bank.jd.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
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
