package ru.bank.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bank.jd.dto.enumerated.ApplicationStatus;
import ru.bank.jd.dto.enumerated.ChangeType;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StatementStatusHistoryDto {
    private ApplicationStatus status;
    private LocalDateTime time;
    private ChangeType changeType;
}
