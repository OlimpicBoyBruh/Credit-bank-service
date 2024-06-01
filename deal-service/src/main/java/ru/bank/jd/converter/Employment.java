package ru.bank.jd.converter;

import lombok.Data;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Position;

@Data
public class Employment {
    private String employmentId;
    private EmploymentStatus status;
    private String employerInn;
    private Double salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
