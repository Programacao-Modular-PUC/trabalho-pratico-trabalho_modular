package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public class TarifaClienteFrequente extends TarifaComMultiplicador {

    public TarifaClienteFrequente() {
        super(TipoTarifa.CLIENTE_FREQUENTE, "Desconto para clientes frequentes.", 0.90);
    }
}
