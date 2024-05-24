package ru.bank.jd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bank.api.dto.LoanStatementRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
@Component
public class PreScoringData {
    private static final String REGEX_EMAIL = "^[\\w.+\\-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$";
    private static final String REGEX_DIGITS = "\\d+";
    private static final String REGEX_LATIN_LETTERS = "^[a-zA-Zа-яА-Я]*$";

    public void validate(LoanStatementRequestDto requestDto) {
        if (checkName(requestDto.getFirstName())) {
            throw new IllegalArgumentException("Имя не может быть меньше 2 или больше 30 латинских букв: "
                    + requestDto.getFirstName());
        }
        if (requestDto.getMiddleName() != null && checkName(requestDto.getMiddleName())) {
            throw new IllegalArgumentException("Отчество не может быть меньше 2 или больше 30 латинских букв: "
                    + requestDto.getMiddleName());
        }
        if (checkName(requestDto.getLastName())) {
            throw new IllegalArgumentException("Фамилия не может быть меньше 2 или больше 30 латинских букв: "
                    + requestDto.getLastName());
        }
        if (requestDto.getAmount().compareTo(BigDecimal.valueOf(30_000)) < 0) {
            throw new IllegalArgumentException("Сумма не может быть меньше 30 000 рублей: " + requestDto.getAmount());
        }
        if (requestDto.getTerm().compareTo(6) < 0) {
            throw new IllegalArgumentException("Срок кредит не может быть меньше 6 месяцев: " + requestDto.getTerm());
        }
        if (LocalDate.now().getYear() - requestDto.getBirthdate().getYear() < 18) {
            throw new IllegalArgumentException("Меньше 18 лет!");
        }
        if (!requestDto.getEmail().matches(REGEX_EMAIL)) {
            throw new IllegalArgumentException("Неверный формат электронной почты: " + requestDto.getEmail());
        }
        if (!requestDto.getPassportSeries().matches(REGEX_DIGITS) ||
                requestDto.getPassportSeries().length() != 4) {
            throw new IllegalArgumentException("Неверный формат серии паспорта: " + requestDto.getPassportSeries());
        }
        if (!requestDto.getPassportNumber().matches(REGEX_DIGITS) ||
                requestDto.getPassportNumber().length() != 6) {
            throw new IllegalArgumentException("Неверный формат номера паспорта: " + requestDto.getPassportNumber());
        }
        log.info("Validate complete!");
    }

    private boolean checkName(String name) {
        return !name.matches(REGEX_LATIN_LETTERS) || name.length() < 2 || name.length() >= 30;
    }
}
