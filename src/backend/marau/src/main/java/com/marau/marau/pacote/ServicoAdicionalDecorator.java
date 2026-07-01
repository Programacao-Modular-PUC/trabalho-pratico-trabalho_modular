package com.marau.marau.pacote;

import com.marau.marau.enums.TipoServicoAdicional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public abstract class ServicoAdicionalDecorator implements PacoteHospedagem {

    private final PacoteHospedagem pacote;

    protected ServicoAdicionalDecorator(PacoteHospedagem pacote) {
        if (pacote == null) {
            throw new IllegalArgumentException("Pacote de hospedagem não informado.");
        }
        this.pacote = pacote;
    }

    protected abstract TipoServicoAdicional getTipoServico();

    @Override
    public String getNome() {
        return pacote.getNome();
    }

    @Override
    public double getValorHospedagem() {
        return pacote.getValorHospedagem();
    }

    @Override
    public double getValorTotal() {
        return arredondar(pacote.getValorTotal() + getTipoServico().getValor());
    }

    @Override
    public List<TipoServicoAdicional> getServicosAdicionais() {
        List<TipoServicoAdicional> servicos = new ArrayList<>(pacote.getServicosAdicionais());
        servicos.add(getTipoServico());
        return servicos;
    }

    @Override
    public List<String> getDescricoesServicos() {
        List<String> descricoes = new ArrayList<>(pacote.getDescricoesServicos());
        TipoServicoAdicional servico = getTipoServico();
        descricoes.add(servico.getNome() + " - R$ " + String.format("%.2f", servico.getValor()).replace('.', ','));
        return descricoes;
    }

    private double arredondar(double valor) {
        return BigDecimal.valueOf(valor)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
