package ru.bank.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.jd.model.dto.CreditDto;
import ru.bank.jd.model.dto.LoanOfferDto;
import ru.bank.jd.model.dto.LoanStatementRequestDto;
import ru.bank.jd.model.dto.ScoringDataDto;
import ru.bank.jd.service.CalculatorService;
import java.util.List;


@RestController
@RequestMapping("/calculator")
@AllArgsConstructor
@Validated
@Tag(name = "Calculator service", description = "The service calculates the terms of the loans.")
public class CalculatorController {

    CalculatorService calculatorService;

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
    public List<LoanOfferDto> searchOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        return calculatorService.generateLoanOffers(loanStatementRequestDto);
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
    public CreditDto validationData(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        return calculatorService.prepareCredit(scoringDataDto);
    }

}
