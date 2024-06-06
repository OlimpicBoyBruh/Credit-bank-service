package ru.bank.jd.junit.util;

import org.junit.jupiter.api.Test;
import ru.bank.jd.util.AnalysisBirthdate;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnalysisBirthdateTest {

    @Test
    void validateThrowException() {
        assertThrows(IllegalArgumentException.class, () -> AnalysisBirthdate.validate(LocalDate.now()));
    }

    @Test
    void validateSuccessfully() {
        assertDoesNotThrow(() -> AnalysisBirthdate.validate(LocalDate.of(2000, 5, 20)));
    }
}