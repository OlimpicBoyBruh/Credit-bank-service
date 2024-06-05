package ru.bank.jd.junit.mapping;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.dto.PaymentScheduleElementDto;
import ru.bank.jd.entity.Credit;
import ru.bank.jd.mapping.CreditMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditMapperTest {
    private final CreditMapper creditMapper = Mappers.getMapper(CreditMapper.class);
    private static final BigDecimal EXPECTED_AMOUNT = new BigDecimal("100000.00");
    private static final int EXPECTED_TERM = 6;
    private static final BigDecimal EXPECTED_MONTHLY_PAYMENT = new BigDecimal("18357.48");
    private static final BigDecimal EXPECTED_RATE = new BigDecimal("16.71");
    private static final BigDecimal EXPECTED_PSK = new BigDecimal("110144.88");
    private static final boolean EXPECTED_INSURANCE_ENABLED = true;
    private static final boolean EXPECTED_SALARY_CLIENT = true;

    @Test
    void creditDtoToCredit() {
        Credit credit = creditMapper.creditDtoToCredit(getCreditDto());


        CreditDto expectedCreditDto = getCreditDto();

        assertAll(
                () -> assertEquals(EXPECTED_AMOUNT, credit.getAmount()),
                () -> assertEquals(EXPECTED_TERM, credit.getTerm()),
                () -> assertEquals(EXPECTED_MONTHLY_PAYMENT, credit.getMonthlyPayment()),
                () -> assertEquals(EXPECTED_RATE, credit.getRate()),
                () -> assertEquals(EXPECTED_PSK, credit.getPsk()),
                () -> assertEquals(EXPECTED_INSURANCE_ENABLED, credit.isInsuranceEnabled()),
                () -> assertEquals(EXPECTED_SALARY_CLIENT, credit.isSalaryClient()),
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