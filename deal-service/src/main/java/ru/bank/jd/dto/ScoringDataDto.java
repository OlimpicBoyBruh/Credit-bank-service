package ru.bank.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoringDataDto {
    /**
     * Сумма.
     */
    @Schema(description = "Amount.", example = "100000.00")
    @NotNull(message = "Сумма не должно быть пустой или null")
    @DecimalMin(value = "30000.0", message = "Сумма кредита должна быть не менее 30000")
    private BigDecimal amount;
    /**
     * Срок.
     */
    @Schema(description = "Term in months.", example = "12")
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    @NotNull(message = "Срок не должен быть пустым или null")
    private Integer term;
    /**
     * Имя.
     */
    @Schema(description = "First name.", example = "Иван")
    @NotBlank(message = "Имя не должно быть пустым или null")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{2,30}$", message = "Имя должно содержать от 2 до 30 символов и состоять только из букв")
    private String firstName;
    /**
     * Фамилия.
     */
    @Schema(description = "Last name.", example = "Петров")
    @NotBlank(message = "Фамилия не должно быть пустым или null")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{2,30}$", message = "Фамилия должна содержать от 2 до 30 символов и состоять только из букв")
    private String lastName;
    /**
     * Отчество.
     */
    @Schema(description = "Middle name.", example = "Сергеевич")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{0,30}$", message = "Имя должно содержать от 2 до 30 символов и состоять только из букв")
    private String middleName;
    /**
     * Пол.
     */
    @Schema(description = "Gender.", example = "MALE")
    @NotNull
    private Gender gender;
    /**
     * Дата рождения.
     */
    @Schema(description = "Date of birth.", example = "2004-07-15")
    @NotNull
    private LocalDate birthdate;
    /**
     * Серия паспорта.
     */
    @Schema(description = "Passport series.", defaultValue = "1234")
    @NotBlank(message = "Серия паспорта не должна быть пустой или null")
    @Pattern(regexp = "\\d{4}+", message = "Серия паспорта должна состоять из 4 цифр")
    private String passportSeries;
    /**
     * Номер паспорта.
     */
    @Schema(description = "Passport number.", defaultValue = "567890")
    @NotBlank(message = "Номер паспорта не должно быть пустым или null")
    @Pattern(regexp = "\\d{6}+", message = "Номер паспорта должен состоять из 6 цифр")
    private String passportNumber;
    /**
     * Дата получения паспорта.
     */
    @Schema(description = "Passport issue date.", example = "2010-05-20")
    @NotNull
    private LocalDate passportIssueDate;
    /**
     * Отделение по выдаче паспорта.
     */
    @Schema(description = "Passport issue branch.", defaultValue = "Отделение №5")
    @NotNull
    private String passportIssueBranch;
    /**
     * Семейное положение.
     */
    @Schema(description = "Marital status.", defaultValue = "MARRIED")
    @NotNull
    private MaritalStatus maritalStatus;
    /**
     * Сумма заработка.
     */
    @Schema(description = "Amount of dependents.", defaultValue = "2")
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
    @Schema(description = "Account number.", defaultValue = "1234567890")
    @NotNull
    private String accountNumber;
    /**
     * Включена ли страховка.
     */
    @Schema(description = "Is insurance enabled", defaultValue = "true")
    @NotNull
    private Boolean isInsuranceEnabled;
    /**
     * Является зарплатным клиентом.
     */
    @Schema(description = "Is salary client", defaultValue = "true")
    @NotNull
    private Boolean isSalaryClient;
}
