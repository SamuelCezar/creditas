package com.creditas.simulador_emprestimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class SimuladorEmprestimoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimuladorEmprestimoApplication.class, args);
	}

}
