package ru.bank.jd.command.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.bank.jd.dto.PaymentScheduleElementDto;
import ru.bank.jd.dto.StatementDto;
import ru.bank.jd.exception.FileWriteException;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.UUID;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditDocument {

    public static File createDocument(StatementDto statementDto) {
        File document = new File("document-" + UUID.randomUUID() + ".txt");
        List<PaymentScheduleElementDto> paymentList = statementDto.getPaymentSchedule();

        try (FileWriter writer = new FileWriter(document)) {
            writer.append("Кредитный договор №" + statementDto.getStatementId() + "\n");
            writer.append("Фамилия: " + statementDto.getLastName() + "\n");
            writer.append("Имя: " + statementDto.getFirstName() + "\n");
            writer.append("Отчество: " + statementDto.getMiddleName() + "\n");
            writer.append("Дата рождения: " + statementDto.getBirthdate() + "\n");
            writer.append("Сумма кредита: " + statementDto.getAmount() + " рублей. ");
            writer.append("Ежемесячный платёж: " + statementDto.getMonthlyPayment() + " рублей ");
            writer.append("Срок: " + statementDto.getTerm() + " месяцев\n");
            writer.append("Полная стоимость кредита: " + statementDto.getPsk() + " рублей\n");
            writer.append("График платежей:\n");

            for (PaymentScheduleElementDto payment : paymentList) {
                writer.append("Номер платежа: " + payment.getNumber() + " ");
                writer.append("Дата платежа: " + payment.getDate() + " ");
                writer.append("Общая сумма платежа: " + payment.getTotalPayment() + " ");
                writer.append("Сумма процентов: " + payment.getInterestPayment() + " ");
                writer.append("Сумма погашения основного долга: " + payment.getDebtPayment() + " ");
                writer.append("Оставшийся долг: " + payment.getRemainingDebt() + " \n");
            }
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            throw new FileWriteException("Ошибка записи файл.");
        }
        return document;
    }
}

