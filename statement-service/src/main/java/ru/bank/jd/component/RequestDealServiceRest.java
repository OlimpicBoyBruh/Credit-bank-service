package ru.bank.jd.component;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bank.jd.client.DealService;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestDealServiceRest {
    private final DealService dealService;

    public List<LoanOfferDto> callCalculateOffer(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Call deal-service: calculate offer");

        try {
            return dealService.getOffers(loanStatementRequestDto);
        } catch (FeignException.FeignClientException exception) {
            log.error("Error while calculating offer: {}", exception.getMessage());
            throw exception;
        }
    }

    public void callSelectOffer(LoanOfferDto loanOfferDto) {
        log.info("Call deal-service: select offer");
        try {
            dealService.selectOffer(loanOfferDto);
        } catch (FeignException.FeignClientException exception) {
            log.error("Error select offer: {}", exception.getMessage());
            throw exception;
        }
    }
}
