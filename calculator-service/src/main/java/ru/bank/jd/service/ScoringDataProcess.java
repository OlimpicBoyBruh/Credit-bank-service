package ru.bank.jd.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.exception.CreditDeniedException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
@SuppressWarnings("java:S5411")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScoringDataProcess {
    private static final BigDecimal twentyFive = new BigDecimal("25");
    private static final BigDecimal hundred = new BigDecimal("100");

    public static BigDecimal getInterestRate(ScoringDataDto scoringDataDto, Double interestRate) {
        log.info("Calculation of the interest rate.");
        Double finalInterestRate = interestRate;
        int age = LocalDate.now().getYear() - scoringDataDto.getBirthdate().getYear();


        if (age < 20 || age > 65) {
            creditDeny("Возраст");
        }

        if (scoringDataDto.getGender().equals(Gender.FEMALE) && age > 31 && age <= 60) {
            finalInterestRate -= 2.75;
        } else if (scoringDataDto.getGender().equals(Gender.MALE) && age > 29 && age <= 55) {
            finalInterestRate -= 2.75;
        } else if (scoringDataDto.getGender().equals(Gender.NOT_BINARY)) {
            finalInterestRate += 7;
        }

        if (scoringDataDto.getEmployment().getEmploymentStatus().equals(EmploymentStatus.UNEMPLOYED)) {
            creditDeny("Не имеет работы");
        } else if (scoringDataDto.getEmployment().getEmploymentStatus().equals(EmploymentStatus.SELF_EMPLOYED)) {
            finalInterestRate++;
        } else if (scoringDataDto.getEmployment().getEmploymentStatus().equals(EmploymentStatus.BUSINESS_OWNER)) {
            finalInterestRate += 2;
        }

        BigDecimal requestedAmount = scoringDataDto.getAmount();
        BigDecimal maxAmount = scoringDataDto.getEmployment().getSalary().multiply(twentyFive);
        if (requestedAmount.compareTo(maxAmount) > 0) {
            creditDeny("Запрошена слишком большая сумма");
        }

        if (scoringDataDto.getEmployment().getWorkExperienceTotal() < 18
                || scoringDataDto.getEmployment().getWorkExperienceCurrent() < 3) {
            creditDeny("Слишком маленький стаж");
        }

        if (scoringDataDto.getMaritalStatus().equals(MaritalStatus.MARRIED)) {
            finalInterestRate -= 1.64;
        } else if (scoringDataDto.getMaritalStatus().equals(MaritalStatus.DIVORCED)) {
            finalInterestRate++;
        }
        if (scoringDataDto.getIsInsuranceEnabled()) {
            finalInterestRate -= 1;
        }
        if (scoringDataDto.getIsSalaryClient()) {
            finalInterestRate -= 0.65;
        }
        log.info("The calculation is completed. The interest rate is " + finalInterestRate);
        return BigDecimal.valueOf(finalInterestRate).setScale(2, RoundingMode.DOWN).divide(hundred);
    }

    private static void creditDeny(String message) {
        log.info("Credit deny, reason: " + message);
        throw new CreditDeniedException("Отказ по кредитной заявке (Не для клиента: " + message + ")");
    }
}
