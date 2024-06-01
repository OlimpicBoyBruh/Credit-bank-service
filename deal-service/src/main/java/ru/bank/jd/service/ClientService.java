package ru.bank.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.converter.Passport;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.entity.Client;
import ru.bank.jd.mapping.ClientMapper;
import ru.bank.jd.repository.ClientRepository;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    @Transactional
    public Client save(LoanStatementRequestDto loanStatementRequestDto) {
        Client client = ClientMapper.INSTANCE.RequestDtoToClient(loanStatementRequestDto);
        clientRepository.save(client);
        return client;
    }
    public Client getById(UUID clientId) {
        return clientRepository.getReferenceById(clientId);
    }
}
