package ru.bank.api.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentScheduleElementDto {
    private Integer number;
    private LocalDate date;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;
    private BigDecimal debtPayment;
    private BigDecimal remainingDebt;

    @Override
    public String toString() {
        return "PaymentScheduleElementDto{" +
                "number=" + number +
                ", date=" + date +
                ", totalPayment=" + totalPayment +
                ", interestPayment=" + interestPayment +
                ", debtPayment=" + debtPayment +
                ", remainingDebt=" + remainingDebt +
                '}';
    }
}
