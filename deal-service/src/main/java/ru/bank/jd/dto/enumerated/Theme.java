package ru.bank.jd.dto.enumerated;

public enum Theme {
    FINISH_REGISTRATION("finish-registration"),
    CREATE_DOCUMENTS("create-documents"),
    SEND_DOCUMENTS("send-documents"),
    SEND_SES("send-ses"),
    STATEMENT_DENIED("statement-denied"),
    CREDIT_ISSUED("credit-issued");

    private final String themeValue;

    Theme(String themeValue) {
        this.themeValue = themeValue;
    }

    public String toString() {
        return this.themeValue;
    }
}