package ru.bank.jd.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.bank.jd.dto.PaymentScheduleElementDto;
import ru.bank.jd.dto.StatementDto;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditDocument {
    public static File createDocument(StatementDto statementDto) {
        File document = new File("src/main/resources/document.txt");
        List<PaymentScheduleElementDto> paymentList = statementDto.getPaymentSchedule();

        try (FileWriter writer = new FileWriter(document)) {
            document.createNewFile();
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
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи файл.");
        }
        return document;
    }
}

