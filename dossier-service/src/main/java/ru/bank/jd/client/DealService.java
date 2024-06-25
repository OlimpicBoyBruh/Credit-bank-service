package ru.bank.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bank.jd.dto.StatementDto;

@FeignClient(name = "deal-service", url = "${integration.deal.base-url}")
public interface DealService {
    @GetMapping(value = "${integration.deal.method.path-statement}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    StatementDto getStatement(@RequestParam("statementId") String statementId);

    @PutMapping(value = "${integration.deal.method.path-update-status}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void updateStatementStatus(@PathVariable("statementId") String statementId);
}
