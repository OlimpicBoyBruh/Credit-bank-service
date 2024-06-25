package ru.bank.jd.dto.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Theme {
    FINISH_REGISTRATION("finish-registration"),
    CREATE_DOCUMENTS("create-documents"),
    SEND_DOCUMENTS("send-documents"),
    SEND_SES("send-ses"),
    STATEMENT_DENIED("statement-denied"),
    CREDIT_ISSUED("credit-issued");
    private final String themeValue;
}