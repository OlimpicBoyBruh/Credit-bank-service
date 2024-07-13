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
import ru.bank.jd.dto.enumerated.CreditStatus;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.service.CreditRepositoryService;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@Transactional
class CreditRepositoryServiceTest {
    @Autowired
    private CreditRepositoryService creditRepositoryService;

    @DynamicPropertySource
    static void configuration(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

    }

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.8-alpine"));


    @Test
    void saveEntityCreditNull() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> creditRepositoryService.save(null));
    }

    @Test
    void saveEntityCredit() {
        Credit credit = new Credit();
        credit.setCreditId(UUID.randomUUID());
        credit.setAmount(new BigDecimal("50000"));
        credit.setCreditStatus(CreditStatus.CALCULATED);
        Credit creditSave = creditRepositoryService.save(credit);

        assertAll(
                () -> assertEquals(new BigDecimal("50000"), creditSave.getAmount()),
                () -> assertEquals(CreditStatus.CALCULATED, creditSave.getCreditStatus()));
    }
}
