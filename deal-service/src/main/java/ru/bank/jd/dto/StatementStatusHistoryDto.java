package ru.bank.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bank.jd.dto.enumerated.ChangeType;
import ru.bank.jd.dto.enumerated.StatementStatus;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StatementStatusHistoryDto {
    private StatementStatus status;
    private LocalDateTime time;
    private ChangeType changeType;
}
