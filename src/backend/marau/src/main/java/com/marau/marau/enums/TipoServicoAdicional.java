package com.marau.marau.enums;

public enum TipoServicoAdicional {

    CAFE_DA_MANHA(
            "Café da manhã",
            "Refeição matinal para os hóspedes.",
            30.00),

    PASSEIO_TURISTICO(
            "Passeio turístico",
            "Passeio guiado por pontos turísticos da região.",
            120.00),

    TRANSPORTE(
            "Transporte",
            "Transporte local durante a hospedagem.",
            80.00),

    LAVANDERIA(
            "Lavanderia",
            "Serviço de lavagem de roupas durante a estadia.",
            45.00),

    TRASLADO_AEROPORTO(
            "Traslado aeroporto-hospedagem",
            "Transporte entre aeroporto e hospedagem.",
            100.00);

    private final String nome;
    private final String descricao;
    private final double valor;

    TipoServicoAdicional(String nome, String descricao, double valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }
}
