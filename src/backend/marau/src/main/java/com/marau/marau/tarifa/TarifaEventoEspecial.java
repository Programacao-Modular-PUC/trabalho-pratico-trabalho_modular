package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public class TarifaEventoEspecial extends TarifaComMultiplicador {

    public TarifaEventoEspecial() {
        super(TipoTarifa.EVENTO_ESPECIAL, "Acrescimo aplicado durante eventos especiais.", 1.50);
    }
}
