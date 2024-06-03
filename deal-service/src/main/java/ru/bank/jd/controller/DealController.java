package ru.bank.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bank.jd.dto.FinishRegistrationRequestDto;
import ru.bank.jd.dto.LoanOfferDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.service.ManagerService;
import ru.bank.jd.service.StatementService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/deal")
@Valid
@RequiredArgsConstructor
@Tag(name = "Deal service", description = "Сохраняет данные в БД.")
public class DealController {
    private final ManagerService managerService;

    /**
     * Принимает данные для расчета возможных предложений, отправляет запрос в сервис Calculator для расчета.
     *
     * @param loanStatementRequestDto данные для расчета условий.
     * @param request                 http запрос.
     * @return Список условий.
     */
    @Operation(summary = "Расчет возможных условий.",
            description = "Возвращает 4 предложения с разными условиями на выбор, отправляется запрос в calculate service."
    )
    @PostMapping("/statement")
    public List<LoanOfferDto> calculationPossibleLoan(@RequestBody @Valid @Parameter(description = "Кредитная заявка.")
                                                      LoanStatementRequestDto loanStatementRequestDto,
                                                      HttpServletRequest request) {
        log.info("invoke: /statement");
        log.debug("Received loanStatementRequestDto: {}", loanStatementRequestDto);
        return managerService.getLoanOffer(loanStatementRequestDto, request.getSession().getId());
    }

    /**
     * Клиент выбирает заявку, которая сохраняется в БД.
     *
     * @param loanOfferDto выбранная заявка.
     */
    @Operation(summary = "Выбор предложения.",
            description = "Выбранное предложение сохраняется в БД."
    )
    @PostMapping("/offer/select")
    public void selectLoanOffer(@RequestBody @Valid @Parameter(description = "Выбранная заявка.")
                                LoanOfferDto loanOfferDto) {
        log.info("invoke: /offer/select");
        log.debug("Received loanOfferDto: {}", loanOfferDto);
        managerService.selectLoanOffer(loanOfferDto);
    }

    /**
     * Приходят полные данные для точного расчета условий по кредиту(ставки, платежи и тд).
     *
     * @param statementId                  номер заявки.
     * @param finishRegistrationRequestDto подробные данные клиента.
     */
    @Operation(summary = "Расчет финальных условий.",
            description = "Происходит финальный расчет всех параметров, отправляется запрос в calculate service."
    )
    @PostMapping("/calculate/{statementId}")
    public void completionOfRegistration(@PathVariable("statementId") @Parameter(description = "Номер заявки.")
                                         String statementId,
                                         @Valid @RequestBody @Parameter(description = "Полные данные клиента.")
                                         FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("invoke: /calculate/{statementId}");
        log.debug("Received finishRegistrationRequestDto: {}", finishRegistrationRequestDto);
        managerService.calculateCredit(finishRegistrationRequestDto, statementId);
    }
}
