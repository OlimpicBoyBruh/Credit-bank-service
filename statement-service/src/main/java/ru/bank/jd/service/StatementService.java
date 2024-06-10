package ru.bank.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.component.RequestDealServiceRest;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.util.AnalysisBirthdate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatementService {
    private final RequestDealServiceRest requestDealServiceRest;

    public List<LoanOfferDto> searchOffer(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("invoke method: searchOffer.");
        AnalysisBirthdate.validate(loanStatementRequestDto.getBirthdate());
        return requestDealServiceRest.callCalculateOffer(loanStatementRequestDto);
    }

    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("invoke method: selectOffer.");
        requestDealServiceRest.callSelectOffer(loanOfferDto);
    }
}
