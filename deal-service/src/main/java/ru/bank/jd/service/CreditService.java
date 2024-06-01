package ru.bank.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.repository.CreditRepository;

@RequiredArgsConstructor
@Service
public class CreditService {
    private final CreditRepository creditRepository;

    @Transactional
    public void save(Credit credit) {
        creditRepository.save(credit);
    }
}
