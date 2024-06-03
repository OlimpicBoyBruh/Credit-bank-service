package ru.bank.jd.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.bank.jd.configuration.AppProperties;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.dto.ScoringDataDto;
import java.util.LinkedHashMap;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class RequestTemplateCalculationRest {
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;
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
                (appProperties.getUrlOffer(), request, List.class);
        assert response != null : "Response is null";
        return response.stream()
                .map(offer -> objectMapper.convertValue(offer, LoanOfferDto.class))
                .toList();
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
        CreditDto response = restTemplate.postForObject(appProperties.getUrlCalc(), request, CreditDto.class);
        if (response != null) {
            return response;
        } else {
            throw new IllegalArgumentException("Response is null");
        }
    }
}
