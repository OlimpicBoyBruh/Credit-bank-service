package ru.bank.api.dto;

import ru.bank.api.statement.EmploymentStatus;
import ru.bank.api.statement.Position;
import java.math.BigDecimal;

public class EmploymentDto {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
