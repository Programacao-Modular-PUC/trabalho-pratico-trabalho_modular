package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public class TarifaFeriado extends TarifaComMultiplicador {

    public TarifaFeriado() {
        super(TipoTarifa.FERIADO, "Acrescimo aplicado em feriados.", 1.20);
    }
}
