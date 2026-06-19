package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public class TarifaAltaTemporada extends TarifaComMultiplicador {

    public TarifaAltaTemporada() {
        super(TipoTarifa.ALTA_TEMPORADA, "Acrescimo para periodos de alta procura.", 1.30);
    }
}
