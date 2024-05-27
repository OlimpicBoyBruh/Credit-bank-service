package ru.bank.jd.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bank.jd.configuration.AppProperties;
import ru.bank.jd.model.dto.LoanOfferDto;
import ru.bank.jd.model.dto.LoanStatementRequestDto;

@Slf4j
@Component
@AllArgsConstructor
public class CreditOfferFactory {
    private final AppProperties appProperties;

    public LoanOfferDto createLoanOffer(boolean isSalaryClient, boolean isInsuranceEnabled,
                                        LoanStatementRequestDto loanStatementRequestDto) {

        PreScoringDataProcess preScoringDataProcess = new PreScoringDataProcess(isSalaryClient, isInsuranceEnabled,
                appProperties.getInterestRate(), appProperties.getInsuranceRate(),
                loanStatementRequestDto.getTerm(), loanStatementRequestDto.getAmount());
        log.info("Creating credit offer: isSalaryClient - "
                + isSalaryClient + ", isInsuranceEnabled - " + isInsuranceEnabled);
        return preScoringDataProcess.createOffer();
    }
}
