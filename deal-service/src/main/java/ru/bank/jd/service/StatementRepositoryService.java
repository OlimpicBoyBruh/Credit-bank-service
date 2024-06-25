package ru.bank.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.dto.StatementStatusHistoryDto;
import ru.bank.jd.dto.api.StatementDto;
import ru.bank.jd.dto.enumerated.ApplicationStatus;
import ru.bank.jd.dto.enumerated.ChangeType;
import ru.bank.jd.entity.Statement;
import ru.bank.jd.mapping.StatementMapper;
import ru.bank.jd.repository.StatementRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatementRepositoryService {
    private final StatementRepository statementRepository;
    private final StatementMapper statementMapper;

    @Transactional
    public Statement save(Statement statement) {
        log.info("invoke save statement entity.");
        return statementRepository.save(statement);
    }

    public Statement getReferenceById(UUID statementId) {
        log.info("invoke getById statement ID: {}", statementId);
        return statementRepository.getReferenceById(statementId);
    }

    public StatementDto getbyIdStatementDto(UUID statementId) {

        log.info("Invoke getbyIdStatementDto statement ID: {}", statementId);
        return statementMapper.statementToStatementDto(statementRepository.getReferenceById(statementId));
    }

    public void updateStatusStatement(UUID statementId, ApplicationStatus status) {
        log.info("Update statement: {} , new status: {}", statementId, status);
        Statement statement = statementRepository.getReferenceById(statementId);
        statement.getStatusHistory().add(new StatementStatusHistoryDto(status, LocalDateTime.now(), ChangeType.AUTOMATIC));
        statement.setStatus(status.toString());
        statementRepository.save(statement);
    }

    public void updateStatusStatement(Statement statement, ApplicationStatus status) {
        log.info("Update statement: {} , new status: {}", statement, status);
        statement.getStatusHistory().add(new StatementStatusHistoryDto(status, LocalDateTime.now(), ChangeType.AUTOMATIC));
        statement.setStatus(status.toString());
    }

    public void updateSesCode(UUID statementId) {
        log.info("Update sesCode statementId: {}", statementId);
        Statement statement = getReferenceById(statementId);
        statement.setSesCode(UUID.randomUUID().toString());
        save(statement);
    }

    public void verifySesCode(UUID statementId, String sesCode) {
        log.info("Verify ses code statementId: {}", statementId);
        Statement statement = getReferenceById(statementId);
        if (!(statement.getSesCode() == null) && statement.getSesCode().equals(sesCode)) {
            updateStatusStatement(statement, ApplicationStatus.DOCUMENT_SIGNED);
            updateStatusStatement(statement, ApplicationStatus.CREDIT_ISSUED);
            save(statement);
        } else {
            log.error("The ses-code is incorrect, statementId: {}", statementId);
            throw new IllegalArgumentException("The ses-code is incorrect, statementId: " + statementId);
        }
    }

}
