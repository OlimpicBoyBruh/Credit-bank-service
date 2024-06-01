package ru.bank.jd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishRegistrationRequestDto {
    /**
     * Пол. Может принимать значения из перечисления Gender.
     */
    private Gender gender;
    /**
     * Семейное положение. Может принимать значения из перечисления MaritalStatus.
     */
    private MaritalStatus maritalStatus;
    /**
     * Количество иждивенцев.
     */
    private Integer dependentAmount;
    /**
     * Дата выдачи паспорта.
     */
    private LocalDate passportIssueDate;
    /**
     * Орган, выдавший паспорт.
     */
    private String passportIssueBranch;
    /**
     * Информация о занятости. Содержит данные типа EmploymentDto.
     */
    private EmploymentDto employment;
    /**
     * Номер счета.
     */
    private String accountNumber;
}
