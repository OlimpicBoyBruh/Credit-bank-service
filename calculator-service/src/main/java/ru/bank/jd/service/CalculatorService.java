package ru.bank.jd.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bank.api.dto.LoanOfferDto;
import ru.bank.api.dto.LoanStatementRequestDto;
import ru.bank.jd.configuration.AppProperties;
import ru.bank.jd.model.AnalysisPossibleOffer;
import ru.bank.jd.model.PreScoringData;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculatorService {
    private PreScoringData preScoringData;
    private AppProperties appProperties;

    public List<LoanOfferDto> prepOffers(LoanStatementRequestDto loanStatementRequestDto) {
        preScoringData.validate(loanStatementRequestDto);
        return Arrays.asList(createOffers(true, true, loanStatementRequestDto),
                createOffers(false, true, loanStatementRequestDto),
                createOffers(true, false, loanStatementRequestDto),
                createOffers(false, false, loanStatementRequestDto));
    }

    private LoanOfferDto createOffers(boolean isSalaryClient, boolean isInsuranceEnabled,
                                      LoanStatementRequestDto loanStatementRequestDto) {
        AnalysisPossibleOffer analysisPossibleOffer =
                new AnalysisPossibleOffer(isSalaryClient, isInsuranceEnabled,
                        appProperties.getInterestRate(), appProperties.getInsuranceRate(),
                        loanStatementRequestDto.getTerm(), loanStatementRequestDto.getAmount());
        return analysisPossibleOffer.createOffer();
    }
}
