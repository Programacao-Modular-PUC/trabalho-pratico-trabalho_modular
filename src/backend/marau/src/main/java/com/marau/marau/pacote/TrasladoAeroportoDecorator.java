package com.marau.marau.pacote;

import com.marau.marau.enums.TipoServicoAdicional;

public class TrasladoAeroportoDecorator extends ServicoAdicionalDecorator {

    public TrasladoAeroportoDecorator(PacoteHospedagem pacote) {
        super(pacote);
    }

    @Override
    protected TipoServicoAdicional getTipoServico() {
        return TipoServicoAdicional.TRASLADO_AEROPORTO;
    }
}
