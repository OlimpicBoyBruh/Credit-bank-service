package ru.bank.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.entity.Statement;
import ru.bank.jd.repository.StatementRepository;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatementRepositoryService {
    private final StatementRepository statementRepository;

    @Transactional
    public Statement save(Statement statement) {
        log.info("invoke save statement entity.");
        return statementRepository.save(statement);
    }

    public Statement getReferenceById(UUID statementId) {
        log.info("invoke getById statement ID: {}", statementId);
        return statementRepository.getReferenceById(statementId);
    }
}
