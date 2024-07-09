package ru.bank.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.client.DealService;
import ru.bank.jd.client.StatementService;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayService {
    private final DealService dealService;
    private final StatementService statementService;

    public List<LoanOfferDto> requestForProposals(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("call calculator-service, calculate offers.");
        return statementService.calculateOffer(loanStatementRequestDto);
    }

    public void selectOfferRequest(LoanOfferDto loanOfferDto) {
        log.info("call calculator-service, select offers.");
        statementService.selectOffer(loanOfferDto);
        log.info("Successfully select offer.");
    }

    public void callSendDocument(String statementId) {
        log.info("call deal-service, send document.");
        dealService.sendDocument(statementId);
        log.info("Successfully send document.");
    }

    public void CallSignDocument(String statementId) {
        log.info("call deal-service, sign document.");
        dealService.signDocument(statementId);
        log.info("Successfully sign document.");
    }

    public void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        log.info("call deal-service, full calculation of the application.");
        dealService.calculateCredit(finishRegistrationRequestDto, statementId);
        log.info("call deal-service, full calculation of the application successfully.");
    }

    public void verifyCode(String statementId, String sesCode) {
        log.info("call deal-service, verify sesCode.");
        dealService.verifySesCode(statementId, sesCode);
        log.info("Successfully verify.");
    }
}
