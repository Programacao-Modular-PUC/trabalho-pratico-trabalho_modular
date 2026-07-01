package com.marau.marau.pacote;

import com.marau.marau.enums.TipoServicoAdicional;

public class TransporteDecorator extends ServicoAdicionalDecorator {

    public TransporteDecorator(PacoteHospedagem pacote) {
        super(pacote);
    }

    @Override
    protected TipoServicoAdicional getTipoServico() {
        return TipoServicoAdicional.TRANSPORTE;
    }
}
