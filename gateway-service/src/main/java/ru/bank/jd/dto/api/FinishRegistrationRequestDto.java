package ru.bank.jd.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bank.jd.dto.EmploymentDto;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import java.time.LocalDate;

/**
 * Класс описывает данный, необходимый для дооформления заявки.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на завершение регистрации")
public class FinishRegistrationRequestDto {
    /**
     * Пол. Может принимать значения из перечисления Gender.
     */
    @NotNull(message = "Пол не может быть пустой или null")
    @Schema(description = "Семейное положение", defaultValue = "MALE")
    private Gender gender;
    /**
     * Семейное положение. Может принимать значения из перечисления MaritalStatus.
     */
    @NotNull(message = "Семейное положение не должно быть пустым или null")
    @Schema(description = "Семейное положение", defaultValue = "MARRIED")
    private MaritalStatus maritalStatus;
    /**
     * Количество иждивенцев.
     */
    @NotNull(message = "Кол-во иждивенцев не должна быть пустым или null")
    @Schema(description = "Количество иждивенцев", defaultValue = "2")
    private Integer dependentAmount;
    /**
     * Дата выдачи паспорта.
     */
    @NotNull(message = "Дата выдачи паспорта не должна быть пустой или null")
    @Schema(description = "Дата выдачи паспорта", defaultValue = "2010-05-20")
    private LocalDate passportIssueDate;
    /**
     * Орган, выдавший паспорт.
     */
    @NotBlank(message = "Отделение, выдавшее паспорт не должно быть пустым или null")
    @Schema(description = "Отделение, выдавшее паспорт", defaultValue = "Отделение №5")
    private String passportIssueBranch;
    /**
     * Информация о занятости. Содержит данные типа EmploymentDto.
     */
    @NotNull(message = "Информация о занятости не должна быть пустой или null")
    @Schema(description = "Информация о занятости")
    private EmploymentDto employment;
    /**
     * Номер счета.
     */
    @NotBlank(message = "Номер счета не должен быть пустым или null")
    @Schema(description = "Номер счета", defaultValue = "1234567890")
    private String accountNumber;
}
