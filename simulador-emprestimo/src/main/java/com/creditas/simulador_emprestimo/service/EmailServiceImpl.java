package com.creditas.simulador_emprestimo.service;

import com.creditas.simulador_emprestimo.service.data.output.SimulacaoEmprestimoServiceOutput;
import com.creditas.simulador_emprestimo.service.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void enviaEmail(SimulacaoEmprestimoServiceOutput simulacao) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("samuelcezar@outlook.com");
        email.setSubject("Simulação de empréstimo está pronta!");
        email.setText(montarCorpoEmail(simulacao));
        javaMailSender.send(email);
    }

    private String montarCorpoEmail(SimulacaoEmprestimoServiceOutput simulacao) {
        return "Detalhes da simulação de empréstimo:\n" +
                "Valor do Empréstimo: " + simulacao.getValorEmprestimo() + "\n" +
                "Prazo (meses): " + simulacao.getPrazoMeses() + "\n" +
                "Taxa de Juros Anual: " + simulacao.getJurosAnual() + "\n" +
                "Taxa de Juros Mensal: " + simulacao.getJurosMensal() + "\n" +
                "Parcela Mensal: " + simulacao.getParcelaMensal() + "\n" +
                "Valor Total a Pagar: " + simulacao.getValorTotalAPagar() + "\n" +
                "Total de Juros Pagos: " + simulacao.getTotalJurosPagos() + "\n";
    }
}
