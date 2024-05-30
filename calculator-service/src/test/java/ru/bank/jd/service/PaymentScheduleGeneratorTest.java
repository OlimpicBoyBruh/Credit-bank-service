package ru.bank.jd.service;

import org.junit.jupiter.api.Test;
import ru.bank.jd.dto.PaymentScheduleElementDto;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentScheduleGeneratorTest {

    @Test
    void generatePaymentScheduleTest() {
        PaymentScheduleGenerator paymentScheduleGenerator = new PaymentScheduleGenerator();

        List<PaymentScheduleElementDto> paymentScheduleElementDtoList
                = paymentScheduleGenerator.generatePaymentSchedule(new BigDecimal(5000), new BigDecimal(15), 6);

        PaymentScheduleElementDto paymentScheduleElementDto = paymentScheduleElementDtoList.get(0);

        PaymentScheduleElementDto finalPaymentScheduleElementDto = paymentScheduleElementDto;
        assertAll(() -> assertEquals(6, paymentScheduleElementDtoList.size()),
                () -> assertEquals(BigDecimal.valueOf(6298.54), finalPaymentScheduleElementDto.getTotalPayment()),
                () -> assertEquals(1, finalPaymentScheduleElementDto.getNumber()),
                () -> assertEquals(finalPaymentScheduleElementDto.getDebtPayment(), BigDecimal.valueOf(48.54)));

        paymentScheduleElementDto = paymentScheduleElementDtoList.get(5);
        PaymentScheduleElementDto finalPaymentScheduleElementDto1 = paymentScheduleElementDto;

        assertAll(() -> assertEquals(finalPaymentScheduleElementDto1.getTotalPayment(), BigDecimal.valueOf(6298.54)),
                () -> assertEquals(6, finalPaymentScheduleElementDto1.getNumber()),
                () -> assertEquals(finalPaymentScheduleElementDto1.getRemainingDebt(), new BigDecimal(0)),
                () -> assertEquals(finalPaymentScheduleElementDto1.getDebtPayment(), BigDecimal.valueOf(2799.51))
        );
    }

    @Test
    void calculateMonthlyPaymentTest() {
        PaymentScheduleGenerator paymentScheduleGenerator = new PaymentScheduleGenerator();

        BigDecimal monthlyPayment =
                paymentScheduleGenerator.calculateMonthlyPayment(new BigDecimal(15), 6, new BigDecimal(5000));
        assertEquals(monthlyPayment, new BigDecimal("6298.54"));
    }
}