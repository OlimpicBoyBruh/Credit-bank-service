# Deal-service

## Описание

Микросервис "Deal-service" path: /deal, предоставляет API для работы с базой данных, отправляет REST запрос в сервис
Calculate для расчета условий по кредиту.

## Технологии

- Java
- Spring Boot
- Liquibase migration
- Postgresql
- Docker

## DataBase entity

- Client
- Credit
- Statement

## API Методы

### Расчет возможных кредитных условий

POST /statement

Этот метод позволяет получить список возможных кредитных предложений на основе предоставленных данных, также сохраняет
данные в сущность Client и Statement.

#### Логика работы:

- По API приходит LoanStatementRequestDto
- На основе LoanStatementRequestDto создаётся сущность Client и сохраняется в БД.
- Создаётся Statement со связью на только что созданный Client и сохраняется в БД.
- Отправляется POST запрос на /calculator/offers МС Калькулятор через FeignClient (здесь и далее вместо FeignClient
  можно использовать RestTemplate)
- Каждому элементу из списка List<LoanOfferDto> присваивается id созданной заявки (Statement)
- Ответ на API - список из 4х LoanOfferDto от "худшего" к "лучшему"..

### Расчет точных условий кредита

POST /offer/select

Этот метод позволяет выбрать одно из предложений.

#### Логика работы:

- По API приходит LoanOfferDto
- Достаётся из БД заявка(Statement) по statementId из LoanOfferDto.
- В заявке обновляется статус, история статусов(List<StatementStatusHistoryDto>), принятое предложение LoanOfferDto
  устанавливается в поле appliedOffer.
- Заявка сохраняется.

### Расчет возможных кредитных условий

POST /calculate/{statementId}

Этот метод позволяет завершить регистрацию, производит полный подсчёт кредита.

#### Логика работы:

- По API приходит объект FinishRegistrationRequestDto и параметр statementId (String).
- Достаётся из БД заявка(Statement) по statementId.
- ScoringDataDto насыщается информацией из FinishRegistrationRequestDto и Client, который хранится в Statement
- Отправляется POST запрос на /calculator/calc МС Калькулятор с телом ScoringDataDto через FeignClient.
- На основе полученного из кредитного конвейера CreditDto создаётся сущность Credit и сохраняется в базу со статусом
  CALCULATED.
- В заявке обновляется статус, история статусов.
- Заявка сохраняется.