package ru.bank.jd.integration.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CalculateOfferControllerTest extends AbstractSpringContextTest {
    @Test
    void calculateOfferSuccessfully() throws Exception {
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
    void calculateOfferNullObject() throws Exception {
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
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateOfferValidateTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = getLoanStatementRequestDto();
        loanStatementRequestDto.setTerm(2);
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
                        .content(mapper.writeValueAsString(loanStatementRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateOfferException() throws Exception {
        wireMockExtension.stubFor(
                WireMock.post("/deal/statement")
                        .willReturn(aResponse()
                                .withStatus(422))
        );
        mockMvc.perform(post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getLoanStatementRequestDto())))
                .andExpect(status().isBadRequest());
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