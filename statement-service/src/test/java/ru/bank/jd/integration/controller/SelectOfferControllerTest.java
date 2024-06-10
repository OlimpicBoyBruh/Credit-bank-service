package ru.bank.jd.integration.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SelectOfferControllerTest extends AbstractSpringContextTest {


    @Test
    void selectOfferSuccessfully() throws Exception {
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

    @Test
    void selectOfferNull() throws Exception {
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
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void selectOfferValidateTest() throws Exception {
        String loanOfferDto = mapper
                .writeValueAsString(getLoanOfferDto());
        LoanOfferDto loanOfferDtoTest = getLoanOfferDto();
        loanOfferDtoTest.setMonthlyPayment(null);
        wireMockExtension.stubFor(
                WireMock.post("/deal/offer/select")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(loanOfferDto)
                                .withStatus(200))
        );
        mockMvc.perform(post("/statement/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loanOfferDtoTest)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void selectOfferException() throws Exception {
        wireMockExtension.stubFor(
                WireMock.post("/deal/offer/select")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withStatus(422))
        );
        mockMvc.perform(post("/statement/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getLoanOfferDto())))
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