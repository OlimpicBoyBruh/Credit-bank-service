package ru.bank.jd.mapping;

import org.junit.jupiter.api.Test;
import ru.bank.jd.dto.EmploymentDto;
import ru.bank.jd.dto.FinishRegistrationRequestDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.dto.enumerated.Position;
import ru.bank.jd.entity.Client;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {

    @Test
    void requestDtoToClient() {
        Client client = ClientMapper.INSTANCE.requestDtoToClient(getLoanStatementRequestDto());

        assertAll(
                () -> assertEquals("Иван", client.getFirstName()),
                () -> assertEquals("Иванов", client.getLastName()),
                () -> assertEquals("Иванович", client.getMiddleName()),
                () -> assertEquals(LocalDate.of(1990, 5, 15), client.getBirthdate()),
                () -> assertEquals("567890", client.getPassport().getNumber()),
                () -> assertEquals("1234", client.getPassport().getSeries()),
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
        ClientMapper.INSTANCE.finishRegistrationRequestDtoToClient(getFinishRegistrationRequestDto(), client);

        assertAll(
                () -> assertNull(client.getFirstName()),
                () -> assertNull(client.getLastName()),
                () -> assertNull(client.getMiddleName()),
                () -> assertNull(client.getBirthdate()),
                () -> assertNull(client.getPassport().getNumber()),
                () -> assertNull( client.getPassport().getSeries()),
                () -> assertNull(client.getClientId()),
                () -> assertEquals(Gender.MALE, client.getGender()),
                () -> assertEquals(MaritalStatus.MARRIED, client.getMaritalStatus()),
                () -> assertEquals(2, client.getDependentAmount()),
                () -> assertNull(client.getPassport().getPassportId()),
                () -> assertEquals("Отделение №5",client.getPassport().getIssueBranch()),
                () -> assertEquals(LocalDate.of(2010, 5, 20),client.getPassport().getIssueDate())
        );

    }

    public FinishRegistrationRequestDto getFinishRegistrationRequestDto() {
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