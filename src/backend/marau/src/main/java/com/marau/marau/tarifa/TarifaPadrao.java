package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public class TarifaPadrao extends TarifaComMultiplicador {

    public TarifaPadrao() {
        super(TipoTarifa.PADRAO, "Calculo normal da diaria.", 1.0);
    }
}
