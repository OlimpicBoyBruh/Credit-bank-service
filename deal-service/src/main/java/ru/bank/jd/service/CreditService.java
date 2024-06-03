package ru.bank.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.repository.CreditRepository;
@Slf4j
@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;

    @Transactional
    public Credit save(Credit credit) {
        log.info("invoke save credit entity.");
        return creditRepository.save(credit);
    }
}
