package ru.bank.jd.converter;

import jakarta.persistence.Enumerated;
import lombok.Data;
import ru.bank.jd.dto.enumerated.ChangeType;
import java.util.Date;

@Data
public class StatusHistory {
    private String status;
    private Date time;
    @Enumerated
    private ChangeType changeType;
}
