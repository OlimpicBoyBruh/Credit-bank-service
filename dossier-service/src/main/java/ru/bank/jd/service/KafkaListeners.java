package ru.bank.jd.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.bank.jd.component.RequestDealServiceRest;
import ru.bank.jd.dto.EmailMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaListeners {
    public final EmailService emailService;
    public final RequestDealServiceRest requestDealServiceRest;

    @KafkaListener(topics = {"finish-registration", "create-documents", "statement-denied", "credit-issued"},
            groupId = "dealGroup")
    private void sendEmailMessage(@Valid EmailMessage data) {
        log.info("Receiver topic: {}", data.getTheme().toString());
        emailService.sendSimpleEmail(data);
    }

    @KafkaListener(topics = "send-documents", groupId = "dealGroup")
    private void finishRegistration(@Valid EmailMessage data) {
        log.info("Receiver topic: {}", data.getTheme().toString());
        emailService.sendMimeMessageDocument(data, requestDealServiceRest.getStatementDto(data.getStatementId()));
        requestDealServiceRest.updateStatusHistory(data.getStatementId());
    }

    @KafkaListener(topics = "send-ses", groupId = "dealGroup")
    private void sendSesCode(@Valid EmailMessage data) {
        log.info("Receiver topic: {}", data.getTheme().toString());
        emailService.sendSesCode(data, requestDealServiceRest.getStatementDto(data.getStatementId()).getSesCode());
    }
}

