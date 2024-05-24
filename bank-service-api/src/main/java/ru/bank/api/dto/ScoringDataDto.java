package ru.bank.api.dto;

import lombok.Getter;
import lombok.Setter;
import ru.bank.api.statement.Gender;
import ru.bank.api.statement.MaritalStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ScoringDataDto {
    /**
     * Сумма.
     */
    private BigDecimal amount;
    /**
     * Срок.
     */
    private Integer term;
    /**
     * Имя.
     */
    private String firstName;
    /**
     * Фамилия.
     */
    private String lastName;
    /**
     * Отчество.
     */
    private String middleName;
    /**
     * Пол.
     */
    private Gender gender;
    /**
     * Дата рождения.
     */
    private LocalDate birthdate;
    /**
     * Серия паспорта.
     */
    private String passportSeries;
    /**
     * Номер паспорта.
     */
    private String passportNumber;
    /**
     * Дата получения паспорта.
     */
    private LocalDate passportIssueDate;
    /**
     * Отделение по выдаче паспорта.
     */
    private String passportIssueBranch;
    /**
     * Семейное положение.
     */
    private MaritalStatus maritalStatus;
    /**
     * Сумма заработка.
     */
    private Integer dependentAmount;
    /**
     * Трудоустройство.
     */
    private EmploymentDto employment;
    /**
     * Номер счета.
     */
    private String accountNumber;
    /**
     * Включена ли страховка.
     */
    private Boolean isInsuranceEnabled;
    /**
     * Является зарплатным клиентом.
     */
    private Boolean isSalaryClient;
}
