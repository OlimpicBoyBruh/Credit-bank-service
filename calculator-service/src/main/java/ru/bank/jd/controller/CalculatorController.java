package ru.bank.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.service.CalculatorService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
@Validated
@Tag(name = "Calculator service", description = "The service calculates the terms of the loans.")
public class CalculatorController {

    private final CalculatorService calculatorService;

    /**
     * Расчёт возможных условий кредита.
     *
     * @param loanStatementRequestDto запрос кредитной выписки.
     * @return - List<LoanOfferDto> список предложений.
     */
    @Operation(summary = "Creating offers",
            description = "Makes 4 offers, with different loan terms."
    )
    @PostMapping("/offers")
    public List<LoanOfferDto> searchOffers(
            @Valid @RequestBody @Parameter(description = "Credit statement.") LoanStatementRequestDto loanStatementRequestDto) {
        log.info("invoke: /offers");
        log.debug("Received loanStatementRequestDto: {}", loanStatementRequestDto);
        List<LoanOfferDto> loanOfferDtoList =  calculatorService.generateLoanOffers(loanStatementRequestDto);
        log.debug("Sent loanOfferDtoList: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }

    /**
     * Расчёт возможных условий кредита.
     *
     * @param scoringDataDto оценочные данные.
     * @return - CreditDto кредитная заявка.
     */
    @Operation(summary = "Calculation of the final conditions",
            description = "Data is being scoring to calculate credit conditions."
    )
    @PostMapping("/calc")
    public CreditDto validationData(
            @Valid @RequestBody @Parameter(description = "Data for calculating the full conditions.")
            ScoringDataDto scoringDataDto) {
        log.info("invoke: /calc");
        log.debug("Received scoringDataDto: {}", scoringDataDto);
        CreditDto creditDto = calculatorService.prepareCredit(scoringDataDto);
        log.debug("Sent creditDto: {}", creditDto);
        return creditDto;
    }

}
