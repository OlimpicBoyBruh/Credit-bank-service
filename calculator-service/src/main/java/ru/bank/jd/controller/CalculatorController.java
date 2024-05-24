package ru.bank.jd.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.api.dto.CreditDto;
import ru.bank.api.dto.LoanOfferDto;
import ru.bank.api.dto.LoanStatementRequestDto;
import ru.bank.api.dto.ScoringDataDto;
import ru.bank.jd.service.CalculatorService;
import java.util.List;

@RestController
@RequestMapping("/calculator")
@AllArgsConstructor
public class CalculatorController {

    CalculatorService calculatorService;

    /**
     * Расчёт возможных условий кредита.
     *
     * @param loanStatementRequestDto запрос кредитной выписки.
     * @return - List<LoanOfferDto> список предложений.
     */
    @PostMapping("/offers")
    public List<LoanOfferDto> searchOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        return calculatorService.prepOffers(loanStatementRequestDto);
    }

    /**
     * Расчёт возможных условий кредита.
     *
     * @param scoringDataDto оценочные данные.
     * @return - CreditDto кредитная заявка.
     */
    @PostMapping("/calc")
    public CreditDto validationData(@RequestBody ScoringDataDto scoringDataDto) {
        //TODO realize
        return null;
    }

}
