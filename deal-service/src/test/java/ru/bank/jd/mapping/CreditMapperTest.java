package ru.bank.jd.mapping;

import org.junit.jupiter.api.Test;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.dto.PaymentScheduleElementDto;
import ru.bank.jd.entity.Credit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CreditMapperTest {

    @Test
    void creditDtoToCredit() {
        Credit credit = CreditMapper.INSTANCE.creditDtoToCredit(getCreditDto());

        CreditDto expectedCreditDto = getCreditDto();

        assertAll(
                () -> assertEquals(expectedCreditDto.getAmount(), credit.getAmount()),
                () -> assertEquals(expectedCreditDto.getTerm(), credit.getTerm()),
                () -> assertEquals(expectedCreditDto.getMonthlyPayment(), credit.getMonthlyPayment()),
                () -> assertEquals(expectedCreditDto.getRate(), credit.getRate()),
                () -> assertEquals(expectedCreditDto.getPsk(), credit.getPsk()),
                () -> assertEquals(expectedCreditDto.getIsInsuranceEnabled(), credit.isInsuranceEnabled()),
                () -> assertEquals(expectedCreditDto.getIsSalaryClient(), credit.isSalaryClient()),
                () -> assertEquals(expectedCreditDto.getPaymentSchedule().size(), credit.getPaymentSchedule().size())
        );

    }
    private CreditDto getCreditDto() {
       return CreditDto.builder()
                .amount(new BigDecimal("100000.00"))
                .term(6)
                .monthlyPayment(new BigDecimal("18357.48"))
                .rate(new BigDecimal("16.71"))
                .psk(new BigDecimal("110144.88"))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .paymentSchedule(List.of(
                        PaymentScheduleElementDto.builder()
                                .number(1)
                                .date(LocalDate.parse("2024-07-03"))
                                .totalPayment(new BigDecimal("18357.48"))
                                .interestPayment(new BigDecimal("1461.70"))
                                .debtPayment(new BigDecimal("16895.78"))
                                .remainingDebt(new BigDecimal("88074.22"))
                                .build()
                ))
                .build();

    }
}