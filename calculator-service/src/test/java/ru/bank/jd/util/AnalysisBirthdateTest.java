package ru.bank.jd.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SuppressWarnings("java:S5778")
class AnalysisBirthdateTest {
    @Test
    void validateExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> AnalysisBirthdate.validate(LocalDate.now()));

    }

    @Test
    void validateSuccessfullyTest() {
        Assertions.assertDoesNotThrow(() -> AnalysisBirthdate.validate(LocalDate.now().minusYears(18)));

    }

}