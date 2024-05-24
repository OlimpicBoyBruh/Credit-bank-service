package ru.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatementRequestDto {
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
     * Электронная почта.
     */
    private String email;
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
