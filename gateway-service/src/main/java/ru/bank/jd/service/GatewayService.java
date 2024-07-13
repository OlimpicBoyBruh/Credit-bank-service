package ru.bank.jd.service;

import feign.FeignException;
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
        try {
            return statementService.calculateOffer(loanStatementRequestDto);
        } catch (FeignException exception) {
            log.error("Error requestForProposals: {}", exception.getMessage());
            throw exception;
        }
    }

    public void selectOfferRequest(LoanOfferDto loanOfferDto) {
        log.info("call calculator-service, select offers.");
        try {
            statementService.selectOffer(loanOfferDto);
        } catch (FeignException exception) {
            log.error("Error selectOffer: {}", exception.getMessage());
            throw exception;
        }
        log.info("Successfully select offer.");
    }

    public void callSendDocument(String statementId) {
        log.info("call deal-service, send document.");
        try {
            dealService.sendDocument(statementId);
        } catch (FeignException exception) {
            log.error("Error callSendDocument: {}", exception.getMessage());
            throw exception;
        }
        log.info("Successfully send document.");
    }

    public void callSignDocument(String statementId) {
        log.info("call deal-service, sign document.");
        try {
            dealService.signDocument(statementId);
        } catch (FeignException exception) {
            log.error("Error callSignDocument: {}", exception.getMessage());
            throw exception;
        }
        log.info("Successfully sign document.");
    }

    public void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        log.info("call deal-service, full calculation of the application.");
        try {
            dealService.calculateCredit(finishRegistrationRequestDto, statementId);
        } catch (FeignException exception) {
            log.error("Error calculateCredit: {}", exception.getMessage());
            throw exception;
        }
        log.info("call deal-service, full calculation of the application successfully.");
    }

    public void verifyCode(String statementId, String sesCode) {
        log.info("call deal-service, verify sesCode.");
        try {
            dealService.verifySesCode(statementId, sesCode);
        } catch (FeignException exception) {
            log.error("Error verifyCode: {}", exception.getMessage());
            throw exception;
        }
        log.info("Successfully verify code.");
    }
}

