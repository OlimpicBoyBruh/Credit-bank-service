package ru.bank.jd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bank.jd.dto.EmploymentDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.service.CalculatorService;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {
    @MockBean
    private CalculatorService calculatorService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        when(calculatorService.generateLoanOffers(getLoanStatementRequestDto())).thenReturn(null);
        when(calculatorService.prepareCredit(getScoringDataDto())).thenReturn(null);
    }

    @Test
    void searchOffersSuccessfullyTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = getLoanStatementRequestDto();
        String loanStatementJson = mapper.writeValueAsString(loanStatementRequestDto);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanStatementJson))
                .andExpect(status().isOk());
    }

    @Test
    void validationDataSuccessfullyTest() throws Exception {
        ScoringDataDto scoringDataDto = getScoringDataDto();
        String scoringDataJson = mapper.writeValueAsString(scoringDataDto);

        mockMvc.perform(post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoringDataJson))
                .andExpect(status().isOk());
    }

    @Test
    void validationDataUnsuccessfullyTest() throws Exception {
        ScoringDataDto scoringDataDto = getScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(1000));
        String scoringDataJson = mapper.writeValueAsString(scoringDataDto);

        mockMvc.perform(post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoringDataJson))
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

    private ScoringDataDto getScoringDataDto() {
        return ScoringDataDto.builder()
                .amount(new BigDecimal("100000.50"))
                .term(12)
                .firstName("Иван")
                .lastName("Иванов")
                .middleName("Иванович")
                .gender(Gender.MALE)
                .birthdate(LocalDate.of(1990, 5, 15))
                .passportSeries("1234")
                .passportNumber("567890")
                .passportIssueDate(LocalDate.of(2010, 8, 20))
                .passportIssueBranch("Отделение №1")
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .employment(new EmploymentDto())
                .accountNumber("1234567890123456")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
    }
}