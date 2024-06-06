package ru.bank.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.service.StatementService;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/statement")
public class StatementController {
    private final StatementService statementService;

    /**
     * Принимает данные для расчета возможных предложений.
     *
     * @param loanStatementRequestDto данные для расчета условий.
     * @return Список условий.
     */
    @Operation(summary = "Расчет возможных условий.",
            description = "Возвращает 4 предложения с разными условиями на выбор, отправляется запрос в calculate service."
    )
    @PostMapping
    public List<LoanOfferDto> calculateOffer(@RequestBody @Valid @Parameter(description = "Кредитная заявка.")
                                             LoanStatementRequestDto loanStatementRequestDto) {
        log.info("invoke: /statement");
        log.debug("Received loanStatementRequestDto: {}", loanStatementRequestDto);

        return statementService.searchOffer(loanStatementRequestDto);
    }

    /**
     * Клиент выбирает заявку, которая отправляется в deal-service и сохраняется в БД.
     *
     * @param loanOfferDto выбранная заявка.
     */
    @Operation(summary = "Выбор предложения.",
            description = "Выбранное предложение отправляется для сохранения в БД."
    )
    @PostMapping("/offer")
    public void selectOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("invoke: /statement/offer");
        log.debug("Received loanOfferDto: {}", loanOfferDto);
        statementService.selectOffer(loanOfferDto);
    }
}
