package ru.bank.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import ru.bank.jd.service.GatewayService;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Gateway service", description = "Делегирование кредитным конвейером.")
public class GatewayController {
    private final GatewayService gatewayService;

    @Operation(summary = "Расчет возможных условий.",
            description = "Возвращает 4 предложения с разными условиями на выбор, " +
                    "отправляется запрос в calculate service."
    )
    @PostMapping("/statement")
    public List<LoanOfferDto> getLoanOffer(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        log.info("invoke: /statement");
        log.debug("Method getLoanOffer, param: {}", loanStatementRequestDto);
        List<LoanOfferDto> loanOfferList = gatewayService.requestForProposals(loanStatementRequestDto);
        log.debug("Method getLoanOffer, response: {}", loanOfferList);
        return loanOfferList;
    }

    @Operation(summary = "Выбор предложения.",
            description = "Выбранное предложение отправляется для сохранения в БД."
    )
    @PostMapping("/statement/select")
    public void selectLoanOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info("invoke: /statement/select");
        log.debug("Method selectLoanOffer, param: {}", loanOfferDto);
        gatewayService.selectOfferRequest(loanOfferDto);
        log.info("The method selectLoanOffer has completed execution successfully.");
    }

    @Operation(summary = "Расчет финальных условий.",
            description = "Происходит финальный расчет всех параметров, отправляется запрос в calculate service."
    )
    @PostMapping("/statement/registration/{statementId}")
    public void endOfRegistration(@Valid @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                                  @Parameter(description = "ID заявки", example = "bef81020-ec8d-4968-aa3a-d2d9f0e9824f")
                                  @PathVariable("statementId") String statementId) {
        log.info("Invoke method endOfRegistration, param: {}", finishRegistrationRequestDto);
        gatewayService.calculateCredit(finishRegistrationRequestDto, statementId);
        log.info("The method endOfRegistration has completed execution successfully.");
    }

    @Operation(summary = "Создание и отправка сформированных документов.",
            description = "Происходит отправка в dossier-service через kafka для формирования email письма."
    )
    @PostMapping("/document/{statementId}")
    public void createDocumentRequest(
            @Parameter(description = "ID заявки", example = "bef81020-ec8d-4968-aa3a-d2d9f0e9824f")
            @PathVariable("statementId") String statementId) {
        log.info("Invoke method createDocumentRequest, param: {}", statementId);
        gatewayService.callSendDocument(statementId);
        log.info("The method createDocumentRequest has completed execution successfully.");
    }

    @Operation(summary = "Подписание документов.",
            description = "Происходит отправка в dossier-service через kafka для формирования email письма."
    )
    @PostMapping("/document/{statementId}/sign")
    public void documentSignRequest(
            @Parameter(description = "ID заявки", example = "bef81020-ec8d-4968-aa3a-d2d9f0e9824f")
            @PathVariable("statementId") String statementId) {
        log.info("Invoke method documentSignRequest, param: {}", statementId);
        gatewayService.callSignDocument(statementId);
        log.info("The method documentSignRequest has completed execution successfully.");
    }

    @Operation(summary = "Запрос на выдачу кредита.",
            description = "Происходит отправка в dossier-service через kafka для подтверждения выдачи кредита."
    )
    @PostMapping("/document/{statementId}/sign/code")
    public void verifySesCodeRequest(
            @Parameter(description = "ID заявки", example = "bef81020-ec8d-4968-aa3a-d2d9f0e9824f")
            @PathVariable("statementId") String statementId, @RequestParam String sesCode) {
        log.info("Invoke method verifySesCodeRequest, param: {}, {}", statementId, sesCode);
        gatewayService.verifyCode(statementId, sesCode);
        log.info("The method verifySesCodeRequest has completed execution successfully.");
    }


}

