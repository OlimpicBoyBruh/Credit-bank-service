package ru.bank.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import java.util.List;

@FeignClient(name = "deal-service", url = "${integration.deal.base-url}")
public interface DealService {

    @PostMapping(value = "${integration.deal.post-method.path-statement}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<LoanOfferDto> getOffers(LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping(value = "${integration.deal.post-method.path-select-offer}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void selectOffer(LoanOfferDto loanOfferDto);
}