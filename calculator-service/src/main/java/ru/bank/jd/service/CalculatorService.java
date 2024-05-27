package ru.bank.jd.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.model.AnalysisBirthdate;
import ru.bank.jd.model.CreditOfferFactory;
import ru.bank.jd.model.CreditStatementFactory;
import ru.bank.jd.model.dto.CreditDto;
import ru.bank.jd.model.dto.LoanOfferDto;
import ru.bank.jd.model.dto.LoanStatementRequestDto;
import ru.bank.jd.model.dto.ScoringDataDto;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CalculatorService {
    private final CreditOfferFactory creditOfferFactory;
    private final CreditStatementFactory creditStatementFactory;

    public List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        AnalysisBirthdate.validate(loanStatementRequestDto.getBirthdate());
        log.info("Creating credit offers.");
        return Arrays.asList(
                creditOfferFactory.createLoanOffer(true, true, loanStatementRequestDto),
                creditOfferFactory.createLoanOffer(false, true, loanStatementRequestDto),
                creditOfferFactory.createLoanOffer(true, false, loanStatementRequestDto),
                creditOfferFactory.createLoanOffer(false, false, loanStatementRequestDto)
        );
    }

    public CreditDto prepareCredit(ScoringDataDto scoringDataDto) {
        AnalysisBirthdate.validate(scoringDataDto.getBirthdate());
        log.info("Starting creditDto formation.");
        return creditStatementFactory.fillCreditForm(scoringDataDto);
    }

}
