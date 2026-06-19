package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

public interface EstrategiaTarifa {

    TipoTarifa getTipo();

    String getDescricao();

    double calcular(ContextoTarifa contexto);
}
