package ru.bank.jd.model;


import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;


@Slf4j
public class AnalysisBirthdate {
    public static void validate(LocalDate birthdate) {
        if (LocalDate.now().getYear() - birthdate.getYear() < 18) {
            throw new IllegalArgumentException("Меньше 18 лет!");
        }
        log.info("Validate complete!");
    }
}
