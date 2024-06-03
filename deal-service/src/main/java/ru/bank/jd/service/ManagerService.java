package ru.bank.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.component.RequestTemplateCalculationRest;
import ru.bank.jd.dto.*;
import ru.bank.jd.dto.enumerated.ApplicationStatus;
import ru.bank.jd.dto.enumerated.ChangeType;
import ru.bank.jd.dto.enumerated.CreditStatus;
import ru.bank.jd.entity.Client;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.entity.Statement;
import ru.bank.jd.mapping.ClientMapper;
import ru.bank.jd.mapping.CreditMapper;
import ru.bank.jd.mapping.PassportDtoMapper;
import ru.bank.jd.mapping.ScoringDataMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {
    private final ClientService clientService;
    private final CreditService creditService;
    private final StatementService statementService;
    private final RequestTemplateCalculationRest calculationRest;

    public List<LoanOfferDto> getLoanOffer(LoanStatementRequestDto loanStatementRequestDto, String sesCode) {
        log.info("invoke getLoanOffer.");
        Statement statement = Statement.builder()
                .client(clientService.saveFromLoanDto(loanStatementRequestDto))
                .creationDate(LocalDateTime.now())
                .sesCode(sesCode)
                .build();
        statementService.save(statement);
        return calculationRest.callOffers(loanStatementRequestDto).stream()
                .map(offer -> {
                    offer.setStatementId(statement.getStatementId().toString());
                    return offer;
                })
                .toList();
    }

    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        log.info("invoke selectLoanOffer -  LoanOfferDto: {}", loanOfferDto);
        Statement statement = statementService.getReferenceById(UUID.fromString(loanOfferDto.getStatementId()));
        statement.setAppliedOffer(loanOfferDto);
        StatementStatusHistoryDto historyDto =
                new StatementStatusHistoryDto(ApplicationStatus.PREAPPROVAL, LocalDateTime.now(), ChangeType.AUTOMATIC);

        statement.setStatus(historyDto.getStatus().toString());
        statement.setStatusHistory(List.of(historyDto));

        statementService.save(statement);
    }

    public void calculateCredit(FinishRegistrationRequestDto requestDto, String statementId) {
        log.info("Invoke calculateCredit.");
        Statement statement = statementService.getReferenceById(UUID.fromString(statementId));
        Client client = clientService.getById(statement.getClient().getClientId());
        statement.getStatusHistory()
                .add(new StatementStatusHistoryDto(ApplicationStatus.APPROVED, LocalDateTime.now(), ChangeType.AUTOMATIC));
        statement.setStatus(ApplicationStatus.APPROVED.toString());

        ClientMapper.INSTANCE.finishRegistrationRequestDtoToClient(requestDto, client);
        PassportDtoMapper.INSTANCE.updatePassportFromRequestDto(requestDto, client.getPassport());
        clientService.save(client);

        CreditDto creditDto = calculationRest.callCalc(ScoringDataMapper.INSTANCE
                .clientAndFinishRequestAndLoanOfferToScoringDto(client,
                        requestDto, statement.getAppliedOffer()));
        log.debug("Received creditDto: {}", creditDto);
        Credit credit = CreditMapper.INSTANCE.creditDtoToCredit(creditDto);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        statement.setCreditId(credit);
        creditService.save(credit);
    }
}
