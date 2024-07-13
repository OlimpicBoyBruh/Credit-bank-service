package ru.bank.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;

@FeignClient(name = "deal-service", url = "${integration.deal.base-url}")
public interface DealService {

    @PostMapping(value = "${integration.deal.method.path-calculate-credit}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                         @PathVariable("statementId") String statementId);

    @PostMapping(value = "${integration.deal.method.path-send-document}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void sendDocument(@PathVariable String statementId);

    @PostMapping(value = "${integration.deal.method.path-sign-document}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void signDocument(@PathVariable String statementId);

    @PostMapping(value = "${integration.deal.method.path-verify-sesCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void verifySesCode(@PathVariable String statementId, @RequestParam String sesCodeDto);
}
