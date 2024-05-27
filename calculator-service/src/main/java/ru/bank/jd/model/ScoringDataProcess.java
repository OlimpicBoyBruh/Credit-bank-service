package ru.bank.jd.model;

import lombok.extern.slf4j.Slf4j;
import ru.bank.jd.exception.CreditDeniedException;
import ru.bank.jd.model.dto.ScoringDataDto;
import ru.bank.jd.model.statement.EmploymentStatus;
import ru.bank.jd.model.statement.Gender;
import ru.bank.jd.model.statement.MaritalStatus;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
public class ScoringDataProcess {

    public BigDecimal getInterestRate(ScoringDataDto scoringDataDto, Double interestRate) {
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
        BigDecimal maxAmount = scoringDataDto.getEmployment().getSalary().multiply(new BigDecimal(25));
        if (requestedAmount.compareTo(maxAmount) > 0) {
            creditDeny("Запрошена слишком большая сумма");
        }

        int totalExperience = scoringDataDto.getEmployment().getWorkExperienceTotal();
        int currentExperience = scoringDataDto.getEmployment().getWorkExperienceCurrent();
        if (totalExperience < 18 || currentExperience < 3) {
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
        return BigDecimal.valueOf(finalInterestRate).setScale(2, RoundingMode.DOWN).divide(BigDecimal.valueOf(100));
    }

    private void creditDeny(String message) {
        log.info("Credit deny, reason: " + message);
        throw new CreditDeniedException("Отказ по кредитной заявке (Не для клиента: " + message + ")");
    }
}
