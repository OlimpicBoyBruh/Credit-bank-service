package ru.bank.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanStatementRequestDto {
    @Schema(description = "Amount request.", defaultValue = "100000.35")
    @NotNull(message = "Сумма не должна быть пустой или null")
    @DecimalMin(value = "30000.0", message = "Сумма кредита должна быть не менее 30000")
    private BigDecimal amount;

    @Schema(description = "Loan term in months.", defaultValue = "12")
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    @NotNull(message = "Срок не должен быть пустым или null")
    private Integer term;

    @Schema(description = "First name of the applicant.", defaultValue = "Иван")
    @NotBlank(message = "Имя не должно быть пустым или null")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{2,30}$", message = "Имя должно содержать от 2 до 30 символов и состоять только из букв")
    private String firstName;

    @Schema(description = "Last name of the applicant.", defaultValue = "Петров")
    @NotBlank(message = "Фамилия не должна быть пустым или null")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{2,30}$", message = "Фамилия должна содержать от 2 до 30 символов и состоять только из букв")
    private String lastName;

    @Schema(description = "Middle name of the applicant.", defaultValue = "Сергеевич")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{0,30}$", message = "Отчество должно содержать до 30 символов и состоять только из букв")
    private String middleName;

    @Schema(description = "Email address of the applicant.", defaultValue = "ivanpetrov@example.com")
    @NotBlank(message = "Почта не должна быть пустой или null")
    @Pattern(regexp = "^[\\w.+\\-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$", message = "Некорректный формат email")
    private String email;

    @Schema(description = "Date of birth of the applicant in YYYY-MM-DD format.", example = "1990-05-15")
    @NotNull(message = "Дата рождения не должна быть пустой или null")
    private LocalDate birthdate;

    @Schema(description = "Passport series of the applicant.", defaultValue = "1234")
    @NotBlank(message = "Серия паспорта не должна быть пустой или null")
    @Pattern(regexp = "\\d{4}+", message = "Серия паспорта должна состоять из 4 цифр")
    private String passportSeries;

    @Schema(description = "Passport number of the applicant.", defaultValue = "567890")
    @NotBlank(message = "Номер паспорта не должно быть пустым или null")
    @Pattern(regexp = "\\d{6}+", message = "Номер паспорта должен состоять из 6 цифр")
    private String passportNumber;
}