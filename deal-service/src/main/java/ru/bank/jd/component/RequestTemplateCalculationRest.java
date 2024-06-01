package ru.bank.jd.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class RequestTemplateCalculationRest {
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;
    private final ObjectMapper objectMapper;

    public List<LoanOfferDto> callOffers(LoanStatementRequestDto loanStatementRequestDto) {
        HttpEntity<LoanStatementRequestDto> request = new HttpEntity<>(loanStatementRequestDto);
        List<LinkedHashMap<LoanOfferDto,String>> response = restTemplate.postForObject(appProperties.getUrlOffer(), request, List.class);
        assert response != null : "Response is null";
        return response.stream()
                .map(offer -> objectMapper.convertValue(offer, LoanOfferDto.class))
                .toList();
    }

    public CreditDto callCalc(ScoringDataDto scoringDataDto) {
        HttpEntity<ScoringDataDto> request = new HttpEntity<>(scoringDataDto);
        CreditDto response = restTemplate.postForObject(appProperties.getUrlCalc(), request, CreditDto.class);
        assert response != null : "Response is null";
        return response;
    }
}
