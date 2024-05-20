package ru.bank.api.dto;

import ru.bank.api.statement.Theme;

public class EmailMessage {
    private String address;
    private Theme theme;
    private Long statementId;
}
