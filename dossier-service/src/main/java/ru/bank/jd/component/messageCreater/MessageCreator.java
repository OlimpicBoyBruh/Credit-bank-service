package ru.bank.jd.component.messageCreater;

import jakarta.mail.internet.MimeMessage;
import ru.bank.jd.dto.EmailMessage;
import ru.bank.jd.dto.enumerated.Theme;

public interface MessageCreator {
    MimeMessage getMessage(EmailMessage emailMessage);

    Theme getTheme();
}
