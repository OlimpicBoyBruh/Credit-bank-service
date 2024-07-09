package ru.bank.jd.dto.enumerated;

/**
 * Перечисление, представляющее статусы заявки на кредит.
 */
public enum ApplicationStatus {
    /**
     * Заявка находится в стадии предварительного одобрения.
     */
    PREAPPROVAL,
    /**
     * Заявка одобрена.
     */
    APPROVED,
    /**
     * Отказ в выдаче кредитной карты.
     */
    CC_DENIED,
    /**
     * Одобренная заявка кредитной карты.
     */
    CC_APPROVED,
    /**
     * Подготовка документов.
     */
    PREPARE_DOCUMENTS,
    /**
     * Документы созданы.
     */
    DOCUMENT_CREATED,
    /**
     * Отказ клиента.
     */
    CLIENT_DENIED,
    /**
     * Документы подписаны.
     */
    DOCUMENT_SIGNED,
    /**
     * Кредит выдан.
     */
    CREDIT_ISSUED
}
