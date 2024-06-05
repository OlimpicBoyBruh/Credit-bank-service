package ru.bank.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import ru.bank.jd.entity.Client;
import ru.bank.jd.mapping.ClientMapper;
import ru.bank.jd.repository.ClientRepository;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientRepositoryService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public Client saveFromLoanDto(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Invoke save from LoanDto.");
        Client client = clientMapper.requestDtoToClient(loanStatementRequestDto);
        client.getPassport().setPassportId(UUID.randomUUID().toString());
        clientRepository.save(client);
        return client;
    }

    public Client getById(UUID clientId) {
        log.info("invoke getById client ID: {}", clientId);
        return clientRepository.getReferenceById(clientId);
    }

    @Transactional
    public Client save(Client client) {
        log.info("invoke save client entity.");
        log.debug("Save client: {} ", client);
        return clientRepository.save(client);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }
}
