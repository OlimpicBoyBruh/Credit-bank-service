package ru.bank.api.dto;

import ru.bank.api.statement.ChangeType;
import ru.bank.api.statement.StatementStatus;
import java.time.LocalDateTime;

public class StatementStatusHistoryDto {
    private StatementStatus status;
    private LocalDateTime time;
    private ChangeType changeType;
}
