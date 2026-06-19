package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public abstract class TarifaComMultiplicador implements EstrategiaTarifa {

    private final TipoTarifa tipo;
    private final String descricao;
    private final double multiplicador;

    protected TarifaComMultiplicador(TipoTarifa tipo, String descricao, double multiplicador) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.multiplicador = multiplicador;
    }

    @Override
    public TipoTarifa getTipo() {
        return tipo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public double calcular(ContextoTarifa contexto) {
        return contexto.getQuantidadeDiarias()
                * contexto.getValorDiariaBase()
                * multiplicador;
    }
}
