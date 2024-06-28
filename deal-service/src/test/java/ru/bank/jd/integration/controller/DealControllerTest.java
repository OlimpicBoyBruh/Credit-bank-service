package ru.bank.jd.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bank.jd.controller.DealController;
import ru.bank.jd.dto.EmploymentDto;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import ru.bank.jd.dto.enumerated.EmploymentStatus;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import ru.bank.jd.dto.enumerated.Position;
import ru.bank.jd.service.KafkaSender;
import ru.bank.jd.service.ManagerService;
import ru.bank.jd.service.StatementRepositoryService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DealController.class)
class DealControllerTest {
    @MockBean
    private KafkaSender kafkaSender;
    @MockBean
    private StatementRepositoryService repositoryService;
    @MockBean
    private ManagerService managerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        when(managerService.getLoanOffer(getLoanStatementRequestDto())).thenReturn(new ArrayList<>());
    }

    @Test
    void calculateOffersSuccessfullyTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = getLoanStatementRequestDto();
        String loanStatementJson = mapper.writeValueAsString(loanStatementRequestDto);

        mockMvc.perform(post("/deal/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanStatementJson))
                .andExpect(status().isOk());
    }

    @Test
    void selectOfferTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = getLoanStatementRequestDto();
        loanStatementRequestDto.setTerm(3);
        String loanStatementJson = mapper.writeValueAsString(loanStatementRequestDto);

        mockMvc.perform(post("/deal/offer/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanStatementJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validationLoanOfferSuccessfullyTest() throws Exception {
        LoanOfferDto loanOfferDto = getLoanOfferDto();
        String scoringDataJson = mapper.writeValueAsString(loanOfferDto);

        mockMvc.perform(post("/deal/offer/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoringDataJson))
                .andExpect(status().isOk());
    }

    @Test
    void validationLoanOfferUnsuccessfullyTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = getLoanStatementRequestDto();
        loanStatementRequestDto.setAmount(BigDecimal.valueOf(1000));
        String scoringDataJson = mapper.writeValueAsString(loanStatementRequestDto);
        mockMvc.perform(post("/deal/offer/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoringDataJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void completionRegistrationTest() throws Exception {
        FinishRegistrationRequestDto requestDto = getFinishRegistrationRequestDto();
        String requestDtoJson = mapper.writeValueAsString(requestDto);
        mockMvc.perform(post("/deal/calculate/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestDtoJson))
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

    private LoanOfferDto getLoanOfferDto() {
        return new LoanOfferDto("9d7b1171-b25a-4d3d-9c21-54365f5e4eb4",
                new BigDecimal("100000"),
                new BigDecimal("115094.4"),
                12,
                new BigDecimal("9591.2"),
                new BigDecimal("17.35"),
                true,
                true);
    }

    public FinishRegistrationRequestDto getFinishRegistrationRequestDto() {

        return FinishRegistrationRequestDto.builder()
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .passportIssueDate(LocalDate.of(2010, 5, 20))
                .passportIssueBranch("Отделение №5")
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.EMPLOYED)
                        .employerINN("1234567890")
                        .salary(BigDecimal.valueOf(50000.00))
                        .position(Position.MANAGER)
                        .workExperienceCurrent(18)
                        .workExperienceCurrent(3)
                        .build())
                .accountNumber("1234567890123456")
                .build();
    }
}
