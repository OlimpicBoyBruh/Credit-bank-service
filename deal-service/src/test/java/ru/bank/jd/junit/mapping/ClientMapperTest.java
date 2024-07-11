package ru.bank.jd.junit.mapping;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.bank.jd.dto.EmploymentDto;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.dto.enumerated.Position;
import ru.bank.jd.entity.Client;
import ru.bank.jd.mapping.ClientMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientMapperTest {

    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    private static final String EXPECTED_FIRST_NAME = "Иван";
    private static final String EXPECTED_LAST_NAME = "Иванов";
    private static final String EXPECTED_MIDDLE_NAME = "Иванович";
    private static final LocalDate EXPECTED_BIRTHDATE = LocalDate.of(1990, 5, 15);
    private static final String EXPECTED_PASSPORT_NUMBER = "567890";
    private static final String EXPECTED_PASSPORT_SERIES = "1234";

    @Test
    void requestDtoToClient() {
        Client client = clientMapper.requestDtoToClient(getLoanStatementRequestDto());

        assertAll(
                () -> assertEquals(EXPECTED_FIRST_NAME, client.getFirstName()),
                () -> assertEquals(EXPECTED_LAST_NAME, client.getLastName()),
                () -> assertEquals(EXPECTED_MIDDLE_NAME, client.getMiddleName()),
                () -> assertEquals(EXPECTED_BIRTHDATE, client.getBirthdate()),
                () -> assertEquals(EXPECTED_PASSPORT_NUMBER, client.getPassport().getNumber()),
                () -> assertEquals(EXPECTED_PASSPORT_SERIES, client.getPassport().getSeries()),
                () -> assertNull(client.getClientId()),
                () -> assertNull(client.getGender()),
                () -> assertNull(client.getMaritalStatus()),
                () -> assertNull(client.getDependentAmount()),
                () -> assertNull(client.getPassport().getPassportId()),
                () -> assertNull(client.getPassport().getIssueBranch()),
                () -> assertNull(client.getPassport().getIssueDate())
        );
    }

    @Test
    void finishRegistrationRequestDtoToClient() {
        Client client = new Client();
        clientMapper.finishRegistrationRequestDtoToClient(getFinishRegistrationRequestDto(), client);

        assertAll(
                () -> assertNull(client.getFirstName()),
                () -> assertNull(client.getLastName()),
                () -> assertNull(client.getMiddleName()),
                () -> assertNull(client.getBirthdate()),
                () -> assertNull(client.getPassport().getNumber()),
                () -> assertNull(client.getPassport().getSeries()),
                () -> assertNull(client.getClientId()),
                () -> assertEquals(Gender.MALE, client.getGender()),
                () -> assertEquals(MaritalStatus.MARRIED, client.getMaritalStatus()),
                () -> assertEquals(2, client.getDependentAmount()),
                () -> assertNull(client.getPassport().getPassportId()),
                () -> assertEquals("Отделение №5", client.getPassport().getIssueBranch()),
                () -> assertEquals(LocalDate.of(2010, 5, 20), client.getPassport().getIssueDate())
        );

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

    private LoanStatementRequestDto getLoanStatementRequestDto() {
        return LoanStatementRequestDto.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .firstName("Иван")
                .lastName("Иванов")
                .middleName("Иванович")
                .email("ivanov@example.com")
                .birthdate(LocalDate.of(1990, 5, 15))
                .passportSeries("1234")
                .passportNumber("567890")
                .build();
    }
}