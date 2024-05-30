package ru.bank.jd.exception;

public class CreditDeniedException extends RuntimeException {
    public CreditDeniedException() {
        super("Отказ по кредитной заявке");
    }

    public CreditDeniedException(String message) {
        super(message);
    }
}
