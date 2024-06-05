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
import ru.bank.jd.dto.enumerated.ApplicationStatus;
import ru.bank.jd.entity.Client;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.entity.Statement;
import ru.bank.jd.service.ClientRepositoryService;
import ru.bank.jd.service.CreditRepositoryService;
import ru.bank.jd.service.StatementRepositoryService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@Transactional
@SpringBootTest
class StatementRepositoryServiceTest {
    @Autowired
    private StatementRepositoryService statementRepositoryService;
    @Autowired
    private ClientRepositoryService clientRepositoryService;
    @Autowired
    private CreditRepositoryService creditRepositoryService;
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.8-alpine"));

    @DynamicPropertySource
    static void configuration(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

    }

    @Test
    void saveEntityNull() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> statementRepositoryService.save(null));
    }

    @Test
    void
    saveEntity() {
        Statement statement = getStatement();
        assertDoesNotThrow(() -> statementRepositoryService.save(statement));
    }

    @Test
    void getReferenceById() {
        Statement statement = getStatement();
        statement = statementRepositoryService.save(statement);
        Statement statementGet = statementRepositoryService.getReferenceById(statement.getStatementId());

        assertAll(
                () -> assertEquals("ABC123", statementGet.getSesCode()),
                () -> assertEquals(ApplicationStatus.APPROVED.toString(), statementGet.getStatus()));
    }

    private Statement getStatement() {
        Client client = clientRepositoryService.save(getClient());
        Credit credit = creditRepositoryService.save(getCredit());

        return Statement.builder()
                .statementId(UUID.randomUUID())
                .client(client)
                .creditId(credit)
                .status(ApplicationStatus.APPROVED.toString())
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .sesCode("ABC123")
                .appliedOffer(null)
                .statusHistory(new ArrayList<>())
                .build();
    }

    private Credit getCredit() {
        return Credit.builder()
                .creditId(UUID.randomUUID())
                .psk(new BigDecimal("50000"))
                .build();
    }

    private Client getClient() {
        return Client.builder()
                .clientId(UUID.randomUUID())
                .build();
    }
}
