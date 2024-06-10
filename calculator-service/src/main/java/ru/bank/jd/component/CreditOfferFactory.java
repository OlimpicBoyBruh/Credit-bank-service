package ru.bank.jd.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bank.jd.configuration.AppProperties;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.service.OfferService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditOfferFactory {
    private final AppProperties appProperties;
    private final OfferService offerService;

    /**
     * Creating offers.
     *
     * @param isSalaryClient          Is salary client
     * @param isInsuranceEnabled      IS insurance enabled
     * @param loanStatementRequestDto data request
     * @return loan offer
     */
    public LoanOfferDto createLoanOffer(boolean isSalaryClient, boolean isInsuranceEnabled,
                                        LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Creating credit offer: isSalaryClient - "
                + isSalaryClient + ", isInsuranceEnabled - " + isInsuranceEnabled);
        return offerService.createOffer(isSalaryClient, isInsuranceEnabled,
                appProperties.getInterestRate(), appProperties.getInsuranceRate(),
                loanStatementRequestDto.getTerm(), loanStatementRequestDto.getAmount());
    }
}
