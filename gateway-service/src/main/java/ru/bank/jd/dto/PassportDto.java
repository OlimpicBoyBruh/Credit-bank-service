package ru.bank.jd.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PassportDto {
    /**
     * Идентификатор паспорта клиента.
     */
    private String passportId;
    /**
     * Серия паспорта клиента.
     */
    private String series;
    /**
     * Номер паспорта клиента.
     */
    private String number;
    /**
     * Отделение выдачи паспорта.
     */
    private String issueBranch;
    /**
     * Дата выдачи паспорта.
     */
    private LocalDate issueDate;
}
