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

    @KafkaListener(topics = {"finish-registration", "create-documents", "statement-denied", "credit-issued", "send-ses",
            "send-documents"},
            groupId = "dealGroup")
    public void sendEmailMessage(@Valid EmailMessage data) {
        log.info("Receiver sendEmailMessage topic: {}", data.getTheme());
        try {
            emailService.send(data);
        } catch (Exception exception) {
            log.error("Error send email. {}", exception.getMessage());
            throw exception;
        }
    }
}

