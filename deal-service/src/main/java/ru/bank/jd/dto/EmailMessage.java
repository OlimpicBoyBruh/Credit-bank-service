package ru.bank.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bank.jd.dto.enumerated.Theme;

@Data
@AllArgsConstructor
public class EmailMessage {
    private String statementId;
    private String address;
    private Theme theme;

}
