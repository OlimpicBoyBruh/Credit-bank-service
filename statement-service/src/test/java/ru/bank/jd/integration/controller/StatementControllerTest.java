package ru.bank.jd.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.bank.jd.App;
import ru.bank.jd.controller.StatementController;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {App.class})
@WireMockTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "integration.deal.url-default=${mockServerUrl}")
class StatementControllerTest {

    public final static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StatementController statementController;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setUp(DynamicPropertyRegistry registry) {
        registry.add("mockServerUrl", wireMockExtension::baseUrl);
    }

    @BeforeAll
    public static void initStub() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void calculateOffer() throws Exception {
        String listOffersJson = mapper
                .writeValueAsString(List.of(getLoanOfferDto(), getLoanOfferDto(), getLoanOfferDto(), getLoanOfferDto()));
        wireMockExtension.stubFor(
                WireMock.post("/deal/statement")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(listOffersJson))
        );
        mockMvc.perform(post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getLoanStatementRequestDto())))
                .andExpect(status().isOk());
    }

    @Test
    void selectOffer() throws Exception {
        String loanOfferDto = mapper
                .writeValueAsString(getLoanOfferDto());
        wireMockExtension.stubFor(
                WireMock.post("/deal/offer/select")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(loanOfferDto)
                                .withStatus(200))
        );
        mockMvc.perform(post("/statement/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getLoanOfferDto())))
                .andExpect(status().isOk());

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

    private static LoanOfferDto getLoanOfferDto() {
        return new LoanOfferDto(UUID.randomUUID().toString(),
                new BigDecimal("100000"),
                new BigDecimal("115094.4"),
                12,
                new BigDecimal("9591.2"),
                new BigDecimal("17.35"),
                true,
                true);
    }

}