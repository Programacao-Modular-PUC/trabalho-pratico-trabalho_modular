package com.marau.marau.pacote;

import com.marau.marau.enums.TipoServicoAdicional;

public class CafeDaManhaDecorator extends ServicoAdicionalDecorator {

    public CafeDaManhaDecorator(PacoteHospedagem pacote) {
        super(pacote);
    }

    @Override
    protected TipoServicoAdicional getTipoServico() {
        return TipoServicoAdicional.CAFE_DA_MANHA;
    }
}
