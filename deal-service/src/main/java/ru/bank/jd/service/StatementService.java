package ru.bank.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.component.RequestTemplateCalculationRest;
import ru.bank.jd.dto.*;
import ru.bank.jd.dto.enumerated.ChangeType;
import ru.bank.jd.dto.enumerated.CreditStatus;
import ru.bank.jd.dto.enumerated.StatementStatus;
import ru.bank.jd.entity.Client;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.entity.Statement;
import ru.bank.jd.mapping.CreditMapper;
import ru.bank.jd.mapping.ScoringDataMapper;
import ru.bank.jd.repository.StatementRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatementService {
    private final StatementRepository statementRepository;
    private final ClientService clientService;
    private final RequestTemplateCalculationRest calculationRest;
    private final CreditService creditService;

    @Transactional
    public void save(Statement statement) {
        statementRepository.save(statement);
    }

    public List<LoanOfferDto> getLoanOffer(LoanStatementRequestDto loanStatementRequestDto, String sesCode) {
        Statement statement = Statement.builder()
                .client(clientService.save(loanStatementRequestDto))
                .creationDate(LocalDateTime.now())
                .sesCode(sesCode)
                .build();
        save(statement);
        return calculationRest.callOffers(loanStatementRequestDto).stream()
                .map(offer -> {
                    offer.setStatementId(statement.getStatementId().toString());
                    return offer;
                })
                .toList();
    }

    public Statement getReferenceById(UUID statementId) {
        return statementRepository.getReferenceById(statementId);
    }

    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        Statement statement = getReferenceById(UUID.fromString(loanOfferDto.getStatementId()));


        statement.setAppliedOffer(loanOfferDto);

        StatementStatusHistoryDto historyDto =
                new StatementStatusHistoryDto(StatementStatus.PENDING, LocalDateTime.now(), ChangeType.AUTOMATIC);
        statement.setStatus(historyDto.getStatus().toString());
        statement.setStatusHistory(historyDto);

        save(statement);
    }

    public void calculateCredit(FinishRegistrationRequestDto requestDto, String statementId) {
        Statement statement = getReferenceById(UUID.fromString(statementId));
        log.info("statement: {}", statement);
        Client client = clientService.getById(statement.getClient().getClientId());
        log.info("client: {}", client);
        ScoringDataDto scoringDataDto = ScoringDataMapper.INSTANCE.clientAndFinishRequestAndLoanOfferToScoringDto(client,
                requestDto, statement.getAppliedOffer());
        log.info("ScoringDataDto: {}", scoringDataDto);
        CreditDto creditDto = calculationRest.callCalc(scoringDataDto);
        log.info("creditDto: {}", creditDto);
        Credit credit = CreditMapper.INSTANCE.creditDtoToCredit(creditDto);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        statement.setCreditId(credit);
        log.info("credit: {}", credit);
        creditService.save(credit);
    }

}
