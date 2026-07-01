package com.marau.marau.enums;

public enum TipoPacoteHospedagem {
    ECONOMICO("Pacote Econômico"),
    FAMILIA("Pacote Família"),
    PREMIUM("Pacote Premium"),
    PERSONALIZADO("Pacote Personalizado");

    private final String nome;

    TipoPacoteHospedagem(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
