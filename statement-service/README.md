# Statement-service

## Описание

Микросервис "Statement-service" path: /statement, предоставляет API для работы c другим сервисом deal, производит
прескоринг данных.

## Технологии

- Java
- Spring Boot

## API Методы

### Расчет возможных кредитных условий

POST /statement

Этот метод позволяет получить список возможных кредитных предложений на основе предоставленных данных.

#### Логика работы:

- По API приходит LoanStatementRequestDto
- На основе LoanStatementRequestDto происходит прескоринг.
- Отправляется POST-запрос на /deal/statement в МС deal через FeignClient.
- Ответ на API - список из 4х LoanOfferDto от "худшего" к "лучшему".

### Расчет точных условий кредита

POST /statement/offer
Этот метод позволяет выбрать одно из предложений.

#### Логика работы:

- По API приходит LoanOfferDto
- Отправляется POST-запрос на /deal/offer/select в МС deal через FeignClient.