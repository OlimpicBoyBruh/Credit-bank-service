package ru.bank.jd.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.bank.jd.dto.LoanOfferDto;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertAll;

class PreScoringDataProcessTest {
    @Test
    void createOfferTrueTest() {
        PreScoringDataProcess preScoringDataProcess = new PreScoringDataProcess(new PaymentScheduleGenerator());
        LoanOfferDto loanOfferDto = preScoringDataProcess.createOffer(true, true, 10, 4.97, 6, new BigDecimal("10000"));
        assertAll(() -> Assertions.assertEquals(new BigDecimal("10723.14"), loanOfferDto.getTotalAmount()),
                () -> Assertions.assertEquals(new BigDecimal("1787.19"), loanOfferDto.getMonthlyPayment()),
                () -> Assertions.assertEquals(new BigDecimal("7.35"), loanOfferDto.getRate())
        );

    }

    @Test
    void createOfferFalseTest() {
        PreScoringDataProcess preScoringDataProcess = new PreScoringDataProcess(new PaymentScheduleGenerator());
        LoanOfferDto loanOfferDto = preScoringDataProcess.createOffer(false, false, 10, 4.97, 6, new BigDecimal("10000"));
        assertAll(() -> Assertions.assertEquals(new BigDecimal("10293.66"), loanOfferDto.getTotalAmount()),
                () -> Assertions.assertEquals(new BigDecimal("1715.61"), loanOfferDto.getMonthlyPayment()),
                () -> Assertions.assertEquals(new BigDecimal("10.00"), loanOfferDto.getRate())
        );
    }

    @Test
    void createOfferTrueAndFalseTest() {
        PreScoringDataProcess preScoringDataProcess = new PreScoringDataProcess(new PaymentScheduleGenerator());
        LoanOfferDto loanOfferDto = preScoringDataProcess.createOffer(true, false, 10, 4.97, 6, new BigDecimal("10000"));
        assertAll(() -> Assertions.assertEquals(new BigDecimal("10258.20"), loanOfferDto.getTotalAmount()),
                () -> Assertions.assertEquals(new BigDecimal("1709.70"), loanOfferDto.getMonthlyPayment()),
                () -> Assertions.assertEquals(new BigDecimal("8.80"), loanOfferDto.getRate())
        );

    }

    @Test
    void createOfferFalseAndTrueTest() {
        PreScoringDataProcess preScoringDataProcess = new PreScoringDataProcess(new PaymentScheduleGenerator());
        LoanOfferDto loanOfferDto = preScoringDataProcess
                .createOffer(false, true, 10, 4.97, 6, new BigDecimal("10000"));
        assertAll(() -> Assertions.assertEquals(new BigDecimal("10760.28"), loanOfferDto.getTotalAmount()),
                () -> Assertions.assertEquals(new BigDecimal("1793.38"), loanOfferDto.getMonthlyPayment()),
                () -> Assertions.assertEquals(new BigDecimal("8.55"), loanOfferDto.getRate())
        );

    }
}
