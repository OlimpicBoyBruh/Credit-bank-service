package ru.bank.jd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bank.jd.dto.enumerated.Theme;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    @NotBlank(message = "Id не должно быть пустым или null")
    private String statementId;
    @NotBlank(message = "email не должен быть пустым или null")
    private String address;
    @NotBlank(message = "Тема не должна быть пустой или null")
    private Theme theme;

}
