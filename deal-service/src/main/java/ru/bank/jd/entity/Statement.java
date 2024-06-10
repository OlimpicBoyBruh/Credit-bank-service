package ru.bank.jd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import ru.bank.jd.dto.StatementStatusHistoryDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Statement {
    @Id
    private UUID statementId;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToOne
    @JoinColumn(name = "credit_id")
    private Credit creditId;
    private String status;
    private LocalDateTime creationDate;
    private LocalDateTime signDate;
    private String sesCode;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private LoanOfferDto appliedOffer;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<StatementStatusHistoryDto> statusHistory;
}
