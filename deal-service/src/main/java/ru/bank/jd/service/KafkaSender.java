package ru.bank.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.enumerated.Theme;
import ru.bank.jd.entity.Client;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaSender {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplateMail;
    private final StatementRepositoryService statementRepositoryService;


    public void sendMessageDossierEmail(String statementId, Theme theme) {
        Client client = statementRepositoryService.getReferenceById(UUID.fromString(statementId)).getClient();
        kafkaTemplateMail.send(theme.getThemeValue(), EmailMessage.builder()
                .address(client.getEmail())
                .statementId(statementId)
                .theme(theme)
                .build());
        log.info("send message dossier-service, theme: {}", theme);
    }
}
