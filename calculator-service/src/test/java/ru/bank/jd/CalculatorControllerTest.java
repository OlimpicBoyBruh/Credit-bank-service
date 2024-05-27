package ru.bank.jd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bank.api.dto.EmploymentDto;
import ru.bank.api.dto.LoanStatementRequestDto;
import ru.bank.api.dto.ScoringDataDto;
import ru.bank.api.statement.Gender;
import ru.bank.api.statement.MaritalStatus;
import ru.bank.jd.controller.CalculatorController;
import ru.bank.jd.service.CalculatorService;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {
    @MockBean
    private CalculatorService calculatorService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    LoanStatementRequestDto loanStatementRequestDto;
    ScoringDataDto scoringDataDto;
    @BeforeEach
    public void init() {
        loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(new BigDecimal("100000"));
        loanStatementRequestDto.setTerm(12);
        loanStatementRequestDto.setFirstName("Иван");
        loanStatementRequestDto.setLastName("Иванов");
        loanStatementRequestDto.setMiddleName("Иванович");
        loanStatementRequestDto.setEmail("ivanov@example.com");
        loanStatementRequestDto.setBirthdate(LocalDate.of(1990, 5, 15));
        loanStatementRequestDto.setPassportSeries("1234");
        loanStatementRequestDto.setPassportNumber("567890");

        scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(new BigDecimal("1000.50"));
        scoringDataDto.setTerm(12);
        scoringDataDto.setFirstName("Иван");
        scoringDataDto.setLastName("Иванов");
        scoringDataDto.setMiddleName("Иванович");
        scoringDataDto.setGender(Gender.MALE);
        scoringDataDto.setBirthdate(LocalDate.of(1990, 5, 15));
        scoringDataDto.setPassportSeries("1234");
        scoringDataDto.setPassportNumber("567890");
        scoringDataDto.setPassportIssueDate(LocalDate.of(2010, 8, 20));
        scoringDataDto.setPassportIssueBranch("Отделение №1");
        scoringDataDto.setMaritalStatus(MaritalStatus.MARRIED);
        scoringDataDto.setDependentAmount(2);
        scoringDataDto.setEmployment(new EmploymentDto());
        scoringDataDto.setAccountNumber("1234567890123456");
        scoringDataDto.setIsInsuranceEnabled(true);
        scoringDataDto.setIsSalaryClient(true);

        when(calculatorService.generateLoanOffers(loanStatementRequestDto)).thenReturn(null);
        when(calculatorService.prepareCredit(scoringDataDto)).thenReturn(null);
    }

    @Test
    public void searchOffersTest() throws Exception {
        String loanStatementJson = mapper.writeValueAsString(loanStatementRequestDto);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanStatementJson))
                .andExpect(status().isOk());
    }
    @Test
    public void validationDataTest() throws Exception {
        String scoringDataJson = mapper.writeValueAsString(scoringDataDto);

        mockMvc.perform(post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoringDataJson))
                .andExpect(status().isOk());
    }
}
