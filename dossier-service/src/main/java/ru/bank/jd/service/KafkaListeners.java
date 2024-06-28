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
    public void sendEmailMessage(@Valid EmailMessage data) {
        log.info("Receiver topic: {}", data.getTheme());
        try {
            emailService.sendSimpleEmail(data);
        } catch (Exception exception) {
            log.error("Error send simple email. {}", exception.getMessage());
            throw exception;
        }
    }

    @KafkaListener(topics = "send-documents", groupId = "dealGroup")
    public void finishRegistration(@Valid EmailMessage data) {
        log.info("Receiver topic: {}", data.getTheme());
        try {
            emailService.sendMimeMessageDocument(data, requestDealServiceRest.getStatementDto(data.getStatementId()));
            requestDealServiceRest.updateStatusHistory(data.getStatementId());
        } catch (Exception exception) {
            log.error("Error send finishRegistration email. {}", exception.getMessage());
            throw exception;
        }
    }

    @KafkaListener(topics = "send-ses", groupId = "dealGroup")
    public void sendSesCode(@Valid EmailMessage data) {
        log.info("Receiver topic: {}", data.getTheme());
        try {
            emailService.sendSesCode(data, requestDealServiceRest.getStatementDto(data.getStatementId()).getSesCode());
        } catch (Exception exception) {
            log.error("Error send sesCode email. {}", exception.getMessage());
            throw exception;
        }
    }
}

