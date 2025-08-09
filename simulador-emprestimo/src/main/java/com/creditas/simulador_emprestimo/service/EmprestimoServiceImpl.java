package com.creditas.simulador_emprestimo.service;

import com.creditas.simulador_emprestimo.configs.properties.EnviaEmailProperties;
import com.creditas.simulador_emprestimo.configs.properties.TaxasJurosPorIdadeProperties;
import com.creditas.simulador_emprestimo.service.data.input.SimulacaoEmprestimoServiceInput;
import com.creditas.simulador_emprestimo.service.mappers.EmprestimoServiceMapper;
import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import com.creditas.simulador_emprestimo.service.interfaces.EmailService;
import com.creditas.simulador_emprestimo.service.interfaces.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class EmprestimoServiceImpl implements EmprestimoService {

    private final TaxasJurosPorIdadeProperties taxasPorIdadeProperties;
    private final EnviaEmailProperties enviaEmailProperties;
    private final EmprestimoServiceMapper mapper;
    private final EmailService emailService;

    @Override
    public SimulacaoEmprestimoServiceOutput simular(SimulacaoEmprestimoServiceInput input) {

        var txJurosAnual = calcularTxAnualPelaIdadeCliente(input.getDataNascimento());
        var txJurosMensal = txJurosAnual.divide(BigDecimal.valueOf(12), 3, RoundingMode.HALF_EVEN);
        var parcelaMensal = calcularValorParcelaMensal(input.getValorEmprestimo(), input.getPrazoMeses(), txJurosMensal);

        var valorTotalAPagar = parcelaMensal.multiply(BigDecimal.valueOf(input.getPrazoMeses()));
        var totalJurosPagos = valorTotalAPagar.subtract(input.getValorEmprestimo());

       SimulacaoEmprestimoServiceOutput simulacao = mapper.toSimulacaoEmprestimoServiceOutput(input, txJurosAnual,
                txJurosMensal,
                valorTotalAPagar,
                totalJurosPagos,
                parcelaMensal);

       if(simulacao != null && enviaEmailProperties.isEnviaEmail()) {
           emailService.enviaEmail(simulacao);
       }

       return simulacao;
    }

    private BigDecimal calcularValorParcelaMensal(BigDecimal valorEmprestimo,
                                                  int prazoMeses,
                                                  BigDecimal txJurosMensal) {

        var umMaisJuros = txJurosMensal.add(BigDecimal.ONE);
        var potenciaNegativa = calcularPotenciaNegativa(umMaisJuros, prazoMeses);

        // Fórmula: PMT = PV * r / (1 - (1 + r)^-n)
        var denominador = BigDecimal.ONE.subtract(potenciaNegativa);
        return valorEmprestimo.multiply(txJurosMensal)
                .divide(denominador, 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calcularPotenciaNegativa(BigDecimal base, int expoente) {
        // (1 + r)^(-n) = 1 / (1 + r)^n
        var potencia = base.pow(expoente);
        return BigDecimal.ONE.divide(potencia, 10, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calcularTxAnualPelaIdadeCliente(LocalDate dataNascimento) {

        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade <= 25) {
            return taxasPorIdadeProperties.getAte25();
        } else if (idade <= 40) {
            return taxasPorIdadeProperties.getDe26a40();
        } else if (idade <= 60) {
            return taxasPorIdadeProperties.getDe41a60();
        } else {
            return taxasPorIdadeProperties.getAcima60();
        }
    }
}
