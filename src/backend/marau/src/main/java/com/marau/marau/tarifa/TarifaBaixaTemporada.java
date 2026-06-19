package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public class TarifaBaixaTemporada extends TarifaComMultiplicador {

    public TarifaBaixaTemporada() {
        super(TipoTarifa.BAIXA_TEMPORADA, "Desconto para periodos de baixa procura.", 0.85);
    }
}
