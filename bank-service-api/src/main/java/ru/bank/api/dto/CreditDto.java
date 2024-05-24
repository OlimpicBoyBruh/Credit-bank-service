package ru.bank.api.dto;

import java.math.BigDecimal;
import java.util.List;

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
