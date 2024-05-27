package ru.bank.jd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.bank.jd.model.AnalysisBirthdate;
import java.time.LocalDate;

public class AnalysisBirthdateTest {
    @Test
    public void validateExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class,() ->AnalysisBirthdate.validate(LocalDate.now()));

    }
    @Test
    public void validateSuccessfullyTest() {
        Assertions.assertDoesNotThrow(() ->AnalysisBirthdate.validate(LocalDate.now().minusYears(18)));

    }

}
