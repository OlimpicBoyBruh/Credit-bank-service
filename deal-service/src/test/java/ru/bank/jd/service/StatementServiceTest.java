package ru.bank.jd.service;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@Transactional
class StatementServiceTest {
    @Autowired
    private StatementService statementService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CreditService creditService;
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
        assertThrows(InvalidDataAccessApiUsageException.class, () -> statementService.save(null));
    }

    @Test
    void
    saveEntity() {
        Statement statement = getStatement();
        assertDoesNotThrow(() -> statementService.save(statement));
    }

    @Test
    void getReferenceById() {
        Statement statement = getStatement();
        statement = statementService.save(statement);
        Statement statementGet = statementService.getReferenceById(statement.getStatementId());

        assertAll(
                () -> assertEquals("ABC123", statementGet.getSesCode()),
                () -> assertEquals(ApplicationStatus.APPROVED.toString(), statementGet.getStatus()));
    }

    private Statement getStatement() {
        Client client = clientService.save(getClient());
        Credit credit = creditService.save(getCredit());

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
