package ru.bank.api.dto;

import ru.bank.api.statement.Gender;
import ru.bank.api.statement.MaritalStatus;
import java.time.LocalDate;

public class FinishRegistrationRequestDto {
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    private EmploymentDto employment;
    private String accountNumber;
}