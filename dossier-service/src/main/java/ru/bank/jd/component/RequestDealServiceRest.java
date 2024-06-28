package ru.bank.jd.component;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bank.jd.client.DealService;
import ru.bank.jd.dto.StatementDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestDealServiceRest {
    private final DealService dealService;

    public StatementDto getStatementDto(String statementId) {
        try {
            StatementDto statementDto = dealService.getStatement(statementId);
            log.debug("Receiver getStatement: {}", statementDto);
            return statementDto;
        } catch (FeignException exception) {
            log.error("Error getStatement: {}", exception.getMessage());
            throw exception;
        }
    }

    public void updateStatusHistory(String statementId) {
        try {
            dealService.updateStatementStatus(statementId);
            log.debug("Receiver updateStatusHistory");
        } catch (FeignException exception) {
            log.error("Error updateStatusHistory: {}", exception.getMessage());
            throw exception;
        }
    }
}
