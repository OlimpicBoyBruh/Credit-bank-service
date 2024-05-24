package ru.bank.jd.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AppProperties {
    @Value("${initialization.interest-rate}")
    private double interestRate;
    @Value("${initialization.insurance-rate}")
    private double insuranceRate;
}
