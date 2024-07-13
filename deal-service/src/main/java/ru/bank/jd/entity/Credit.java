package ru.bank.jd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import ru.bank.jd.dto.PaymentScheduleElementDto;
import ru.bank.jd.dto.enumerated.CreditStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Credit {
    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID creditId;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<PaymentScheduleElementDto> paymentSchedule;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;
}
