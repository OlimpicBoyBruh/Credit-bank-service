package ru.bank.jd.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.rest.DealService;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestDealServiceRest {
    private final DealService dealService;

    public List<LoanOfferDto> callCalculateOffer(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Call deal-service: calculate offer");
        return dealService.getOffers(loanStatementRequestDto);
    }

    public void callSelectOffer(LoanOfferDto loanOfferDto) {
        log.info("Call deal-service: select offer");
        dealService.selectOffer(loanOfferDto);
    }
}
