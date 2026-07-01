package com.marau.marau.pacote;

import com.marau.marau.enums.TipoServicoAdicional;

import java.util.ArrayList;
import java.util.List;

public class HospedagemBasica implements PacoteHospedagem {

    private final String nome;
    private final double valorHospedagem;

    public HospedagemBasica(String nome, double valorHospedagem) {
        if (valorHospedagem <= 0) {
            throw new IllegalArgumentException("Valor da hospedagem precisa ser maior que zero.");
        }
        this.nome = nome;
        this.valorHospedagem = valorHospedagem;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getValorHospedagem() {
        return valorHospedagem;
    }

    @Override
    public double getValorTotal() {
        return valorHospedagem;
    }

    @Override
    public List<TipoServicoAdicional> getServicosAdicionais() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getDescricoesServicos() {
        return new ArrayList<>();
    }
}
