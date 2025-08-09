package com.creditas.simulador_emprestimo.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix ="envia-email")
public class EnviaEmailProperties {
    private boolean enviaEmail;
}
