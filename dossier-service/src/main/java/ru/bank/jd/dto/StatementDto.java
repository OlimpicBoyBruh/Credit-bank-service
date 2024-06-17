package ru.bank.jd.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class StatementDto {
    private UUID statementId;
    private String firstName;
    private String middleName;
    private String sesCode;
    private String lastName;
    private LocalDate birthdate;
    private PassportDto passport;
    private UUID creditId;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private List<PaymentScheduleElementDto> paymentSchedule;
    private boolean isInsuranceEnabled;
}
