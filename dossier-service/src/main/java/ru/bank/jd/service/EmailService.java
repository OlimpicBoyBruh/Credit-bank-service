package ru.bank.jd.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.jd.component.messageCreater.MessageCreator;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.enumerated.Theme;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final Map<Theme, MessageCreator> messageCreatorMap;

    public EmailService(JavaMailSender javaMailSender, List<MessageCreator> messageList) {
        this.javaMailSender = javaMailSender;
        this.messageCreatorMap = messageList.stream().collect(Collectors.toMap(MessageCreator::getTheme, Function.identity()));
    }

    @Transactional
    public void send(EmailMessage data) {
        try {
            MessageCreator messageCreator = messageCreatorMap.get(data.getTheme());
            javaMailSender.send(messageCreator.getMessage(data));
            log.info("Send message: Theme: {}, successful", data.getTheme());
        } catch (
                MailException exception) {
            log.error("ошибка отправки email в методе send: {}", exception.getMessage());
            throw exception;
        }
    }
}