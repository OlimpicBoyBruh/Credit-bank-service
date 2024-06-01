package ru.bank.jd.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bank.jd.dto.FinishRegistrationRequestDto;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.service.StatementService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/deal")
@Valid
@RequiredArgsConstructor
public class DealController {
    private final StatementService statementService;

    @PostMapping("/statement")
    public List<LoanOfferDto> calculationPossibleLoan(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto,
                                                      HttpServletRequest request) {
        log.info("invoke: /statement");
        log.debug("Received loanStatementRequestDto: {}", loanStatementRequestDto);
        return statementService.getLoanOffer(loanStatementRequestDto, request.getSession().getId());
    }

    @PostMapping("/offer/select")
    public void selectLoanOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("invoke: /offer/select");
        log.debug("Received loanOfferDto: {}", loanOfferDto);
        statementService.selectLoanOffer(loanOfferDto);
    }

    @PostMapping("/calculate/{statementId}")
    public void completionOfRegistration(@PathVariable("statementId") String statementId,
                                         @Valid @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("invoke: /calculate/{statementId}");
        log.debug("Received finishRegistrationRequestDto: {}", finishRegistrationRequestDto);
        statementService.calculateCredit(finishRegistrationRequestDto, statementId);
    }
}
