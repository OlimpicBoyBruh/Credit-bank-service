package ru.bank.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanStatementRequestDto {
    @NotNull(message = "Сумма не должно быть пустой или null")
    @DecimalMin(value = "30000.0", message = "Сумма кредита должна быть не менее 30000")
    private BigDecimal amount;

    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    @NotNull(message = "Срок не должен быть пустым или null")
    private Integer term;
    @NotBlank(message = "Имя не должно быть пустым или null")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{2,30}$", message = "Имя должно содержать от 2 до 30 символов и состоять только из букв")
    private String firstName;
    @NotBlank(message = "Фамилия не должна быть пустым или null")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{2,30}$", message = "Фамилия должна содержать от 2 до 30 символов и состоять только из букв")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{0,30}$", message = "Отчество должно содержать до 30 символов и состоять только из букв")
    private String middleName;
    @NotBlank(message = "Почта не должна быть пустой или null")
    @Pattern(regexp = "^[\\w.+\\-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$", message = "Некорректный формат email")
    private String email;
    @NotNull
    private LocalDate birthdate;
    @NotBlank(message = "Серия паспорта не должна быть пустой или null")
    @Pattern(regexp = "\\d{4}+", message = "Серия паспорта должна состоять из 4 цифр")
    private String passportSeries;
    @NotBlank(message = "Номер паспорта не должно быть пустым или null")
    @Pattern(regexp = "\\d{6}+", message = "Номер паспорта должен состоять из 6 цифр")
    private String passportNumber;

    @Override
    public String toString() {
        return "LoanStatementRequestDto{" +
                "amount=" + amount +
                ", term=" + term +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", birthdate=" + birthdate +
                ", passportSeries='" + passportSeries + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                '}';
    }
}
