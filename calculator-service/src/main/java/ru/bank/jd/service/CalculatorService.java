package ru.bank.jd.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.util.AnalysisBirthdate;
import ru.bank.jd.component.CreditOfferFactory;
import ru.bank.jd.component.CreditStatementFactory;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.dto.ScoringDataDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor
public class CalculatorService {
    private final CreditOfferFactory creditOfferFactory;
    private final CreditStatementFactory creditStatementFactory;

    public List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        AnalysisBirthdate.validate(loanStatementRequestDto.getBirthdate());
        log.info("Creating credit offers.");
        return Stream.of(
                creditOfferFactory.createLoanOffer(true, true, loanStatementRequestDto),
                creditOfferFactory.createLoanOffer(false, true, loanStatementRequestDto),
                creditOfferFactory.createLoanOffer(true, false, loanStatementRequestDto),
                creditOfferFactory.createLoanOffer(false, false, loanStatementRequestDto)
        ).sorted(Comparator.comparing(LoanOfferDto::getRate)).toList();
    }

    public CreditDto prepareCredit(ScoringDataDto scoringDataDto) {
        AnalysisBirthdate.validate(scoringDataDto.getBirthdate());
        log.info("Starting creditDto formation.");
        return creditStatementFactory.fillCreditForm(scoringDataDto);
    }

}
