package ru.bank.jd.junit.mapping;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.bank.jd.dto.EmploymentDto;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.dto.enumerated.Position;
import ru.bank.jd.entity.Client;
import ru.bank.jd.mapping.ScoringDataMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ScoringDataMapperTest {
    private final ScoringDataMapper scoringDataMapper = Mappers.getMapper(ScoringDataMapper.class);
    private static final Gender EXPECTED_GENDER = Gender.MALE;
    private static final MaritalStatus EXPECTED_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final int EXPECTED_DEPENDENT_AMOUNT = 2;
    private static final EmploymentStatus EXPECTED_EMPLOYMENT_STATUS = EmploymentStatus.EMPLOYED;
    private static final String EXPECTED_EMPLOYER_INN = "1234567890";
    private static final BigDecimal EXPECTED_SALARY = BigDecimal.valueOf(50000.00);
    private static final Position EXPECTED_POSITION = Position.MANAGER;
    private static final BigDecimal EXPECTED_REQUESTED_AMOUNT = new BigDecimal("100000");
    private static final int EXPECTED_TERM = 12;
    private static final boolean EXPECTED_IS_INSURANCE_ENABLED = true;
    private static final boolean EXPECTED_IS_SALARY_CLIENT = true;

    @Test
    void clientAndFinishRequestAndLoanOfferToScoringDto() {
        ScoringDataDto scoringDataDto = scoringDataMapper.clientAndFinishRequestAndLoanOfferToScoringDto(
                getClient(), getFinishRegistrationRequestDto(), getLoanOfferDto());

        assertAll("scoringDataDto fields",
                () -> assertNotNull(scoringDataDto),
                () -> assertEquals(getFinishRegistrationRequestDto().getGender(), scoringDataDto.getGender()),
                () -> assertEquals(EXPECTED_GENDER, scoringDataDto.getGender()),
                () -> assertEquals(EXPECTED_MARITAL_STATUS, scoringDataDto.getMaritalStatus()),
                () -> assertEquals(EXPECTED_DEPENDENT_AMOUNT, scoringDataDto.getDependentAmount()),
                () -> assertEquals(EXPECTED_EMPLOYMENT_STATUS, scoringDataDto.getEmployment().getEmploymentStatus()),
                () -> assertEquals(EXPECTED_EMPLOYER_INN, scoringDataDto.getEmployment().getEmployerINN()),
                () -> assertEquals(EXPECTED_SALARY, scoringDataDto.getEmployment().getSalary()),
                () -> assertEquals(EXPECTED_POSITION, scoringDataDto.getEmployment().getPosition()),
                () -> assertEquals(getFinishRegistrationRequestDto()
                        .getEmployment().getWorkExperienceTotal(), scoringDataDto.getEmployment().getWorkExperienceTotal()),
                () -> assertEquals(getFinishRegistrationRequestDto()
                        .getEmployment().getWorkExperienceCurrent(), scoringDataDto.getEmployment().getWorkExperienceCurrent()),
                () -> assertEquals(EXPECTED_REQUESTED_AMOUNT, scoringDataDto.getAmount()),
                () -> assertEquals(EXPECTED_TERM, scoringDataDto.getTerm()),
                () -> assertEquals(EXPECTED_IS_INSURANCE_ENABLED, scoringDataDto.getIsInsuranceEnabled()),
                () -> assertEquals(EXPECTED_IS_SALARY_CLIENT, scoringDataDto.getIsSalaryClient())
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

    public FinishRegistrationRequestDto getFinishRegistrationRequestDto() {

        return FinishRegistrationRequestDto.builder()
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .passportIssueDate(LocalDate.of(2010, 5, 20))
                .passportIssueBranch("Отделение №5")
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.EMPLOYED)
                        .employerINN("1234567890")
                        .salary(BigDecimal.valueOf(50000.00))
                        .position(Position.MANAGER)
                        .workExperienceCurrent(18)
                        .workExperienceCurrent(3)
                        .build())
                .accountNumber("1234567890123456")
                .build();
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