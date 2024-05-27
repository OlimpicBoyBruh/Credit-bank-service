package ru.bank.jd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bank.jd.model.PreScoringDataProcess;
import ru.bank.jd.model.dto.LoanOfferDto;
import java.math.BigDecimal;

public class PreScoringDataProcessTest {
    private PreScoringDataProcess preScoringDataProcess;
    @BeforeEach
    public void init() {
        preScoringDataProcess = new PreScoringDataProcess(true, true, 10,
                4.495, 6, new BigDecimal("10000"));
    }
    @Test
    public void createOfferTrueTest() {
        preScoringDataProcess = new PreScoringDataProcess(true, true, 10,
                4.95, 6, new BigDecimal("10000"));
        LoanOfferDto loanOfferDto = preScoringDataProcess.createOffer();
        Assertions.assertEquals(loanOfferDto.getTotalAmount(), new BigDecimal("10721.10"));
        Assertions.assertEquals(loanOfferDto.getMonthlyPayment(), new BigDecimal("1786.85"));
        Assertions.assertEquals(loanOfferDto.getRate(),  new BigDecimal("7.35"));
    }
    @Test
    public void createOfferFalseTest() {
        preScoringDataProcess = new PreScoringDataProcess(false, false, 10,
                4.95, 6, new BigDecimal("10000"));
        LoanOfferDto loanOfferDto = preScoringDataProcess.createOffer();
        Assertions.assertEquals(loanOfferDto.getTotalAmount(), new BigDecimal("10293.66"));
        Assertions.assertEquals(loanOfferDto.getMonthlyPayment(), new BigDecimal("1715.61"));
        Assertions.assertEquals(loanOfferDto.getRate(),  new BigDecimal("10.00"));
    }
    @Test
    public void createOfferTrueAndFalseTest() {
        preScoringDataProcess = new PreScoringDataProcess(true, false, 10,
                4.95, 6, new BigDecimal("10000"));
        LoanOfferDto loanOfferDto = preScoringDataProcess.createOffer();
        Assertions.assertEquals(loanOfferDto.getTotalAmount(), new BigDecimal("10258.20"));
        Assertions.assertEquals(loanOfferDto.getMonthlyPayment(), new BigDecimal("1709.70"));
        Assertions.assertEquals(loanOfferDto.getRate(),  new BigDecimal("8.80"));
    }
    @Test
    public void createOfferFalseAndTrueTest() {
        preScoringDataProcess = new PreScoringDataProcess(true, false, 10,
                4.95, 6, new BigDecimal("10000"));
        LoanOfferDto loanOfferDto = preScoringDataProcess.createOffer();
        Assertions.assertEquals(loanOfferDto.getTotalAmount(), new BigDecimal("10258.20"));
        Assertions.assertEquals(loanOfferDto.getMonthlyPayment(), new BigDecimal("1709.70"));
        Assertions.assertEquals(loanOfferDto.getRate(),  new BigDecimal("8.80"));
    }
}
