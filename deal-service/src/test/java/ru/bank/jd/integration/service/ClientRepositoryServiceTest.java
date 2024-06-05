package ru.bank.jd.integration.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.entity.Client;
import ru.bank.jd.service.ClientRepositoryService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
class ClientRepositoryServiceTest {
    @Autowired
    private ClientRepositoryService clientRepositoryService;
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.8-alpine"));

    @DynamicPropertySource
    static void configuration(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

    }

    @Test
    void saveFromLoanDto() {
        LoanStatementRequestDto loanStatementRequestDto = getLoanStatementRequestDto();
        clientRepositoryService.saveFromLoanDto(loanStatementRequestDto);
        Client client = clientRepositoryService.getAll().get(0);
        assertAll(
                () -> assertEquals("Иван", client.getFirstName()),
                () -> assertEquals("Иванов", client.getLastName()),
                () -> assertEquals("ivanov@example.com", client.getEmail()),
                () -> assertEquals("1234", client.getPassport().getSeries()),
                () -> assertEquals("567890", client.getPassport().getNumber()));
    }

    @Test
    void save() {
        Client client = getClient();
        assertDoesNotThrow(() -> clientRepositoryService.save(client));

    }

    @Test
    void getByIdEntity() {
        Client client = getClient();
        Client client1 = clientRepositoryService.save(client);

        Client clientTest = clientRepositoryService.getById(client1.getClientId());
        assertAll(
                () -> assertEquals("John", clientTest.getFirstName()),
                () -> assertEquals("Doe", clientTest.getLastName()),
                () -> assertEquals("john.doe@example.com", clientTest.getEmail()),
                () -> assertEquals(Gender.MALE, clientTest.getGender()),
                () -> assertEquals("1234567890", clientTest.getAccountNumber()));
    }

    @Test
    void saveEntityClientNull() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> clientRepositoryService.save(null));
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