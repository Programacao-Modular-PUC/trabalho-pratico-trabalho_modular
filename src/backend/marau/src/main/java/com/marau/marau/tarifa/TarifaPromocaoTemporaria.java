package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public class TarifaPromocaoTemporaria extends TarifaComMultiplicador {

    public TarifaPromocaoTemporaria() {
        super(TipoTarifa.PROMOCAO_TEMPORARIA, "Desconto de promocao temporaria.", 0.75);
    }
}
