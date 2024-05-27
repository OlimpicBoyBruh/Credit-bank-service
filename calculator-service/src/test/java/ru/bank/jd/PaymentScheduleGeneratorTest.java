package ru.bank.jd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bank.jd.model.PaymentScheduleGenerator;
import ru.bank.jd.model.dto.PaymentScheduleElementDto;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentScheduleGeneratorTest {
    private PaymentScheduleGenerator paymentScheduleGenerator;

    @BeforeEach
    public void init() {
        paymentScheduleGenerator = new PaymentScheduleGenerator(new BigDecimal(5000), new BigDecimal(15), 6);
    }

    @Test
    public void generatePaymentScheduleTest() {
        List<PaymentScheduleElementDto> paymentScheduleElementDtoList = paymentScheduleGenerator.generatePaymentSchedule();
        PaymentScheduleElementDto paymentScheduleElementDto = paymentScheduleElementDtoList.get(0);
        assertEquals(paymentScheduleElementDtoList.size(), 6);
        assertEquals(paymentScheduleElementDto.getTotalPayment(), BigDecimal.valueOf(6298.54));
        assertEquals(paymentScheduleElementDto.getNumber(), 1);
        assertEquals(paymentScheduleElementDto.getDebtPayment(), BigDecimal.valueOf(48.54));
        paymentScheduleElementDto = paymentScheduleElementDtoList.get(5);

        assertEquals(paymentScheduleElementDto.getTotalPayment(), BigDecimal.valueOf(6298.54));
        assertEquals(paymentScheduleElementDto.getNumber(), 6);
        assertEquals(paymentScheduleElementDto.getRemainingDebt(), new BigDecimal(0));
        assertEquals(paymentScheduleElementDto.getDebtPayment(), BigDecimal.valueOf(2799.51));
    }

    @Test
    public void calculateMonthlyPaymentTest() {
        BigDecimal monthlyPayment = paymentScheduleGenerator.calculateMonthlyPayment();
        assertEquals(monthlyPayment,new BigDecimal("6298.54"));
    }
}
