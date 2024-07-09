package ru.bank.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import ru.bank.jd.dto.api.StatementDto;
import ru.bank.jd.dto.enumerated.ApplicationStatus;
import ru.bank.jd.dto.enumerated.Theme;
import ru.bank.jd.entity.Statement;
import ru.bank.jd.service.KafkaSender;
import ru.bank.jd.service.ManagerService;
import ru.bank.jd.service.StatementRepositoryService;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/deal")
@Validated
@RequiredArgsConstructor
@Tag(name = "Deal service", description = "Сохраняет данные в БД.")
public class DealController {
    private final ManagerService managerService;
    private final KafkaSender kafkaSender;
    private final StatementRepositoryService statementRepositoryService;

    /**
     * Принимает данные для расчета возможных предложений, отправляет запрос в сервис Calculator для расчета.
     *
     * @param loanStatementRequestDto данные для расчета условий.
     * @return Список условий.
     */
    @Operation(summary = "Расчет возможных условий.",
            description = "Возвращает 4 предложения с разными условиями на выбор, отправляется запрос в calculate service."
    )
    @PostMapping("/statement")
    public List<LoanOfferDto> calculationPossibleLoan(@RequestBody @Parameter(description = "Кредитная заявка.")
                                                          @Valid LoanStatementRequestDto loanStatementRequestDto) {
        log.info("invoke: /statement");
        log.debug("Received loanStatementRequestDto: {}", loanStatementRequestDto);
        List<LoanOfferDto> loanOfferDtos = managerService.getLoanOffer(loanStatementRequestDto);
        log.debug("Return loanStatementRequestDto: {}", loanOfferDtos);
        return loanOfferDtos;
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

    /**
     * Происходит отправка в dossier-service через kafka для формирования email письма.
     *
     * @param statementId идентификатор заявки
     */

    @Operation(summary = "Отправка сформированных документов.",
            description = "Происходит отправка в dossier-service через kafka для формирования email письма."
    )
    @PostMapping("/document/{statementId}/send")
    public void requestSendDocument(@PathVariable String statementId) {
        statementRepositoryService.updateStatusStatement(UUID.fromString(statementId),
                ApplicationStatus.PREPARE_DOCUMENTS);
        kafkaSender.sendMessageDossierEmail(statementId, Theme.SEND_DOCUMENTS);
        log.info("Successfully send message requestSendDocument.");
    }

    /**
     * Происходит формирование sesCode и отправка  в dossier-service через kafka для формирования email письма
     *
     * @param statementId идентификатор заявки.
     */

    @Operation(summary = "Подписание документов.",
            description = "Происходит отправка в dossier-service через kafka для формирования email письма."
    )
    @PostMapping("/document/{statementId}/sign")
    public void requestSignDocument(@PathVariable String statementId) {
        statementRepositoryService.updateSesCode(UUID.fromString(statementId));
        kafkaSender.sendMessageDossierEmail(statementId, Theme.SEND_SES);
        log.info("Successfully send message requestSignDocument.");
    }

    /**
     * Подтверждение выдачи кредита.
     *
     * @param statementId идентификатор заявки.
     * @param sesCodeDto  код для подтверждения выдачи кредита.
     */

    @Operation(summary = "Запрос на выдачу кредита.",
            description = "Происходит отправка в dossier-service через kafka для подтверждения выдачи кредита."
    )
    @PostMapping("/document/{statementId}/code")
    public void requestSignCode(@PathVariable String statementId, @RequestParam String sesCodeDto) {
        statementRepositoryService.verifySesCode(UUID.fromString(statementId), sesCodeDto);
        kafkaSender.sendMessageDossierEmail(statementId, Theme.CREDIT_ISSUED);
        log.info("Successfully send message requestSignCode.");
    }

    /**
     * Запрос на получение StatementDto.
     *
     * @param statementId идентификатор заявки.
     * @return возвращает данные необходимые для формирования документов.
     */

    @Operation(summary = "Запрос получение StatementDto.",
            description = "Данный объект необходим для формирования документов по кредиту и отправки email."
    )
    @GetMapping("/statement/get")
    public StatementDto getStatementDto(@RequestParam("statementId") String statementId) {
        log.info("Invoke getStatementDto: {}", statementId);
        StatementDto statementDto = statementRepositoryService.getbyIdStatementDto(UUID.fromString(statementId));
        log.debug("Return statementDto: {}", statementDto);
        return statementDto;
    }

    /**
     * Запрос на обновление статуса.
     *
     * @param statementId идентификатор заявки.
     */

    @Operation(summary = "Запрос на обновления статуса заявки.",
            description = "Происходит обновления статуса и истории."
    )
    @PutMapping("/admin/statement/{statementId}/status")
    public void updateStatusStatement(@PathVariable String statementId) {
        log.info("Invoke updateStatusStatement: {}", statementId);
        statementRepositoryService.updateStatusStatement(UUID.fromString(statementId), ApplicationStatus.DOCUMENT_CREATED);
        log.info("Successfully update status statementId: {}", statementId);
    }

    /**
     * Возвращает конкретную заявку по id.
     *
     * @param statementId id заявки.
     * @return найденную заявку по id.
     */

    @Operation(summary = "Запрос на получение заявки по id.",
            description = "Приходит id, по нему из БД возвращается заявка."
    )
    @GetMapping("/admin/statement/{statementId}")
    public Statement getStatementFromId(@PathVariable String statementId) {
        log.info("Invoke getStatementFromId: {}", statementId);
        Statement statement = statementRepositoryService.getReferenceById(UUID.fromString(statementId));
        log.info("return statement: {}", statement);
        return (Statement) Hibernate.unproxy(statement);
    }

    /**
     * Возвращает все statement из БД.
     *
     * @return list заявок.
     */

    @Operation(summary = "Запрос на получение всех заявок.",
            description = "Возвращает List всех заявок в БД."
    )
    @GetMapping("/admin/statement")
    public List<Statement> getAllStatement() {
        log.info("Invoke getAllStatement");
        List<Statement> statements = statementRepositoryService.getAllStatement();
        log.info("return getAllStatement : {}", statements);
        return statements;
    }
}
