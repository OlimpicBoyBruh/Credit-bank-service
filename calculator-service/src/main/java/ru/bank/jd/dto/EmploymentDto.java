package ru.bank.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Position;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDto {
    /**
     * Статус трудоустройства.
     */
    @Schema(description = "Employment status", defaultValue = "EMPLOYED")
    private EmploymentStatus employmentStatus;
    /**
     * ИНН работодателя.
     */
    @Schema(description = "Employer's INN", defaultValue = "1234567890")
    private String employerINN;
    /**
     * Заработная плата.
     */
    @Schema(description = "Salary", defaultValue = "50000.00")
    private BigDecimal salary;
    /**
     * Позиция на работе.
     */
    @Schema(description = "Position", defaultValue = "EMPLOYEE")
    private Position position;
    /**
     * Общий стаж.
     */
    @Schema(description = "Total work experience",  defaultValue = "18")
    private Integer workExperienceTotal;
    /**
     * Стаж на текущем месте.
     */
    @Schema(description = "Work experience at current position", defaultValue = "6")
    private Integer workExperienceCurrent;
}
