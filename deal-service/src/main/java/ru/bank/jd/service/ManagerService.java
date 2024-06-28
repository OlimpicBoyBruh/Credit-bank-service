package ru.bank.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.bank.jd.component.RequestTemplateCalculationRest;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.dto.StatementStatusHistoryDto;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import ru.bank.jd.dto.enumerated.ApplicationStatus;
import ru.bank.jd.dto.enumerated.ChangeType;
import ru.bank.jd.dto.enumerated.CreditStatus;
import ru.bank.jd.dto.enumerated.Theme;
import ru.bank.jd.entity.Client;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.entity.Statement;
import ru.bank.jd.mapping.ClientMapper;
import ru.bank.jd.mapping.CreditMapper;
import ru.bank.jd.mapping.ScoringDataMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ClientRepositoryService clientRepositoryService;
    private final CreditRepositoryService creditRepositoryService;
    private final StatementRepositoryService statementRepositoryService;
    private final RequestTemplateCalculationRest calculationRest;
    private final ClientMapper clientMapper;
    private final ScoringDataMapper scoringDataMapper;
    private final CreditMapper creditMapper;
    private final KafkaSender kafkaSender;


    public List<LoanOfferDto> getLoanOffer(LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> loanOfferDtoList;
        log.info("invoke getLoanOffer.");
        Statement statement = Statement.builder()
                .statementId(UUID.randomUUID())
                .creationDate(LocalDateTime.now())
                .status(String.valueOf(ApplicationStatus.PREAPPROVAL))
                .statusHistory(List
                        .of(new StatementStatusHistoryDto(ApplicationStatus.PREAPPROVAL, LocalDateTime.now(), ChangeType.AUTOMATIC)))
                .build();
        try {

            loanOfferDtoList = calculationRest.callOffers(loanStatementRequestDto).stream()
                    .map(offer -> {
                        offer.setStatementId(statement.getStatementId().toString());
                        return offer;
                    })
                    .toList();

            statement.setClient(clientRepositoryService.saveFromLoanDto(loanStatementRequestDto));
            statementRepositoryService.save(statement);
            return loanOfferDtoList;
        } catch (HttpClientErrorException httpClientErrorException) {
            log.error("Error while calculating offer: {}", httpClientErrorException.getMessage());
            throw httpClientErrorException;
        }
    }

    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        log.info("invoke selectLoanOffer -  LoanOfferDto: {}", loanOfferDto);
        Statement statement = statementRepositoryService.getReferenceById(UUID.fromString(loanOfferDto.getStatementId()));
        statement.setAppliedOffer(loanOfferDto);
        addStatusHistory(statement, ApplicationStatus.APPROVED);
        statementRepositoryService.save(statement);
        kafkaSender.sendMessageDossierEmail(statement.getStatementId().toString(), Theme.FINISH_REGISTRATION);
    }

    public void calculateCredit(FinishRegistrationRequestDto requestDto, String statementId) {
        log.info("Invoke calculateCredit.");

        Statement statement = statementRepositoryService.getReferenceById(UUID.fromString(statementId));
        Client client = clientRepositoryService.getById(statement.getClient().getClientId());

        CreditDto creditDto;
        try {
            creditDto = calculationRest.callCalc(scoringDataMapper
                    .clientAndFinishRequestAndLoanOfferToScoringDto(client, requestDto, statement.getAppliedOffer()));

        } catch (HttpClientErrorException httpClientErrorException) {
            addStatusHistory(statement, ApplicationStatus.CC_DENIED);
            updateClientAndSave(requestDto, client);
            kafkaSender.sendMessageDossierEmail(statement.getStatementId().toString(), Theme.STATEMENT_DENIED);
            log.error("Error while calculating credit: {}", httpClientErrorException.getMessage());
            throw httpClientErrorException;
        }
        log.debug("Received creditDto: {}", creditDto);

        Credit credit = creditMapper.creditDtoToCredit(creditDto);
        credit.setCreditStatus(CreditStatus.CALCULATED);

        addStatusHistory(statement, ApplicationStatus.CC_APPROVED);
        statement.setCreditId(credit);
        creditRepositoryService.save(credit);
        updateClientAndSave(requestDto, client);
        kafkaSender.sendMessageDossierEmail(statement.getStatementId().toString(), Theme.CREATE_DOCUMENTS);
    }

    private void addStatusHistory(Statement statement, ApplicationStatus status) {
        statement.getStatusHistory().add(new StatementStatusHistoryDto(status, LocalDateTime.now(), ChangeType.AUTOMATIC));
        statement.setStatus(status.toString());
    }

    private void updateClientAndSave(FinishRegistrationRequestDto requestDto, Client client) {
        clientMapper.finishRegistrationRequestDtoToClient(requestDto, client);
        clientRepositoryService.save(client);
    }
}
