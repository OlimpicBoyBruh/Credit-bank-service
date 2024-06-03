package ru.bank.jd.mapping;

import org.junit.jupiter.api.Test;
import ru.bank.jd.dto.EmploymentDto;
import ru.bank.jd.dto.FinishRegistrationRequestDto;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.dto.enumerated.Position;
import ru.bank.jd.entity.Client;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ScoringDataMapperTest {

    @Test
    void clientAndFinishRequestAndLoanOfferToScoringDto() {
        ScoringDataDto scoringDataDto = ScoringDataMapper.INSTANCE.clientAndFinishRequestAndLoanOfferToScoringDto(
                getClient(), getFinishRegistrationRequestDto(), getLoanOfferDto());

        assertAll("scoringDataDto fields",
                () -> assertNotNull(scoringDataDto),
                () -> assertEquals(getFinishRegistrationRequestDto().getGender(), scoringDataDto.getGender()),
                () -> assertEquals(getFinishRegistrationRequestDto().getMaritalStatus(), scoringDataDto.getMaritalStatus()),
                () -> assertEquals(getFinishRegistrationRequestDto().getDependentAmount(), scoringDataDto.getDependentAmount()),
                () -> assertEquals(getFinishRegistrationRequestDto().getEmployment().getEmploymentStatus(), scoringDataDto.getEmployment().getEmploymentStatus()),
                () -> assertEquals(getFinishRegistrationRequestDto().getEmployment().getEmployerINN(), scoringDataDto.getEmployment().getEmployerINN()),
                () -> assertEquals(getFinishRegistrationRequestDto().getEmployment().getSalary(), scoringDataDto.getEmployment().getSalary()),
                () -> assertEquals(getFinishRegistrationRequestDto().getEmployment().getPosition(), scoringDataDto.getEmployment().getPosition()),
                () -> assertEquals(getFinishRegistrationRequestDto()
                        .getEmployment().getWorkExperienceTotal(), scoringDataDto.getEmployment().getWorkExperienceTotal()),
                () -> assertEquals(getFinishRegistrationRequestDto()
                        .getEmployment().getWorkExperienceCurrent(), scoringDataDto.getEmployment().getWorkExperienceCurrent()),
                () -> assertEquals(getLoanOfferDto().getRequestedAmount(), scoringDataDto.getAmount()),
                () -> assertEquals(getLoanOfferDto().getTerm(), scoringDataDto.getTerm()),
                () -> assertEquals(getLoanOfferDto().getIsInsuranceEnabled(), scoringDataDto.getIsInsuranceEnabled()),
                () -> assertEquals(getLoanOfferDto().getIsSalaryClient(), scoringDataDto.getIsSalaryClient())
        );
    }

    private Client getClient() {
        return Client.builder()
                .clientId(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .birthdate(LocalDate.of(1980, 5, 15))
                .email("john.doe@example.com")
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(0)
                .accountNumber("1234567890")
                .build();
    }

    private FinishRegistrationRequestDto getFinishRegistrationRequestDto() {
        FinishRegistrationRequestDto finishRegistrationRequestDto = new FinishRegistrationRequestDto();
        finishRegistrationRequestDto.setGender(Gender.MALE);
        finishRegistrationRequestDto.setMaritalStatus(MaritalStatus.MARRIED);
        finishRegistrationRequestDto.setDependentAmount(2);
        finishRegistrationRequestDto.setPassportIssueDate(LocalDate.of(2010, 5, 20));
        finishRegistrationRequestDto.setPassportIssueBranch("Отделение №5");

        EmploymentDto employment = new EmploymentDto();
        employment.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employment.setEmployerINN("1234567890");
        employment.setSalary(BigDecimal.valueOf(50000.00));
        employment.setPosition(Position.MANAGER);
        employment.setWorkExperienceTotal(18);
        employment.setWorkExperienceCurrent(3);

        finishRegistrationRequestDto.setEmployment(employment);
        finishRegistrationRequestDto.setAccountNumber("1234567890123456");

        return finishRegistrationRequestDto;
    }

    private LoanOfferDto getLoanOfferDto() {
        return LoanOfferDto.builder()
                .statementId("707968e9-31d0-48aa-b6aa-c1b33d1c119f")
                .requestedAmount(new BigDecimal("100000"))
                .totalAmount(new BigDecimal("115094.4"))
                .term(12)
                .monthlyPayment(new BigDecimal("9591.2"))
                .rate(new BigDecimal("17.35"))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();

    }
}