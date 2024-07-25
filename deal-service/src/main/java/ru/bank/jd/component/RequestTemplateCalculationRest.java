package ru.bank.jd.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.bank.jd.configuration.IntegrationProperties;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestTemplateCalculationRest {
    private final RestTemplate restTemplate;
    private final IntegrationProperties integrationProperties;
    private final ObjectMapper objectMapper;

    /**
     * Отправляет rest запрос в сервис Calculator.
     *
     * @param loanStatementRequestDto заявка для расчета предварительных условий кредитования.
     * @return 4 предварительных предложения.
     */

    public List<LoanOfferDto> callOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Invoke method callOffer, calculate-service");
        HttpEntity<LoanStatementRequestDto> request = new HttpEntity<>(loanStatementRequestDto);
        List<LinkedHashMap<LoanOfferDto, String>> response = restTemplate.postForObject
                (integrationProperties.getBaseUrl() +
                        integrationProperties.getMethod().getUrlOffer(), request, List.class);
        if (response != null) {
            return response.stream()
                    .map(offer -> objectMapper.convertValue(offer, LoanOfferDto.class))
                    .toList();
        } else {
            throw new IllegalArgumentException("Response is null");
        }
    }

    /**
     * Отправляет rest запрос в сервис Calculator.
     *
     * @param scoringDataDto данные для расчета точных условий по кредиту.
     * @return кредитную заявку
     */
    public CreditDto callCalc(ScoringDataDto scoringDataDto) {
        log.info("Invoke method callCalc, calculate-service");
        HttpEntity<ScoringDataDto> request = new HttpEntity<>(scoringDataDto);
        CreditDto response = restTemplate.postForObject(integrationProperties.getBaseUrl() +
                integrationProperties.getMethod().getUrlCalc(), request, CreditDto.class);
        if (response != null) {
            return response;
        } else {
            throw new IllegalArgumentException("Response is null");
        }
    }
}
