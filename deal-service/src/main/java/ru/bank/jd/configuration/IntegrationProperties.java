package ru.bank.jd.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "integration.calculator")
public class IntegrationProperties {
    @NotNull
    private String baseUrl;

    @NotNull
    private Method method = new Method();

    @Data
    public static class Method {
        @NotNull
        private String urlOffer;

        @NotNull
        private String urlCalc;
    }
}
