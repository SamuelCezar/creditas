package com.creditas.simulador_emprestimo.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties(prefix ="taxas-juros")
public class TaxasJurosPorIdadeProperties {
    private BigDecimal ate25;
    private BigDecimal de26a40;
    private BigDecimal de41a60;
    private BigDecimal acima60;
}
