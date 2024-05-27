package ru.bank.jd.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import ru.bank.jd.model.statement.Gender;
import ru.bank.jd.model.statement.MaritalStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class  ScoringDataDto {
    /**
     * Сумма.
     */
    @NotNull(message = "Сумма не должно быть пустой или null")
    @DecimalMin(value = "30000.0", message = "Сумма кредита должна быть не менее 30000")
    private BigDecimal amount;
    /**
     * Срок.
     */
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    @NotNull(message = "Срок не должен быть пустым или null")
    private Integer term;
    /**
     * Имя.
     */
    @NotBlank(message = "Имя не должно быть пустым или null")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{2,30}$", message = "Имя должно содержать от 2 до 30 символов и состоять только из букв")
    private String firstName;
    /**
     * Фамилия.
     */
    @NotBlank(message = "Фамилия не должно быть пустым или null")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{2,30}$", message = "Фамилия должна содержать от 2 до 30 символов и состоять только из букв")
    private String lastName;
    /**
     * Отчество.
     */
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{0,30}$", message = "Имя должно содержать от 2 до 30 символов и состоять только из букв")
    private String middleName;
    /**
     * Пол.
     */
    @NotNull
    private Gender gender;
    /**
     * Дата рождения.
     */
    @NotNull
    private LocalDate birthdate;
    /**
     * Серия паспорта.
     */
    @NotBlank(message = "Серия паспорта не должна быть пустой или null")
    @Pattern(regexp = "\\d{4}+", message = "Серия паспорта должна состоять из 4 цифр")
    private String passportSeries;
    /**
     * Номер паспорта.
     */
    @NotBlank(message = "Номер паспорта не должно быть пустым или null")
    @Pattern(regexp = "\\d{6}+", message = "Номер паспорта должен состоять из 6 цифр")
    private String passportNumber;
    /**
     * Дата получения паспорта.
     */
    @NotNull
    private LocalDate passportIssueDate;
    /**
     * Отделение по выдаче паспорта.
     */
    @NotNull
    private String passportIssueBranch;
    /**
     * Семейное положение.
     */
    @NotNull
    private MaritalStatus maritalStatus;
    /**
     * Сумма заработка.
     */
    @NotNull
    private Integer dependentAmount;
    /**
     * Трудоустройство.
     */
    @NotNull
    private EmploymentDto employment;
    /**
     * Номер счета.
     */
    @NotNull
    private String accountNumber;
    /**
     * Включена ли страховка.
     */
    @NotNull
    private Boolean isInsuranceEnabled;
    /**
     * Является зарплатным клиентом.
     */
    @NotNull
    private Boolean isSalaryClient;
}
