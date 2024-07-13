package ru.bank.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import java.util.List;

@FeignClient(name = "statement-service", url = "${integration.statement.base-url}")
public interface StatementService {

    @PostMapping(value = "${integration.statement.method.path-calculate-offer}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<LoanOfferDto> calculateOffer(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping(value = "${integration.statement.method.path-select-offer}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);

}
