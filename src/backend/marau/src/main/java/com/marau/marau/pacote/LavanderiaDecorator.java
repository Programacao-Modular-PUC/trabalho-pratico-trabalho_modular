package com.marau.marau.pacote;

import com.marau.marau.enums.TipoServicoAdicional;

public class LavanderiaDecorator extends ServicoAdicionalDecorator {

    public LavanderiaDecorator(PacoteHospedagem pacote) {
        super(pacote);
    }

    @Override
    protected TipoServicoAdicional getTipoServico() {
        return TipoServicoAdicional.LAVANDERIA;
    }
}
