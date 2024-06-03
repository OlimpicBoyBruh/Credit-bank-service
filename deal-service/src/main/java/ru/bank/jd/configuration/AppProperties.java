package ru.bank.jd.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "calculator")
public class AppProperties {
    @NotNull
    private String urlOffer;
    @NotNull
    private String urlCalc;
}
