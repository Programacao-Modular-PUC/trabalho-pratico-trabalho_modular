package com.marau.marau.pacote;

import com.marau.marau.enums.TipoServicoAdicional;

public class PasseioTuristicoDecorator extends ServicoAdicionalDecorator {

    public PasseioTuristicoDecorator(PacoteHospedagem pacote) {
        super(pacote);
    }

    @Override
    protected TipoServicoAdicional getTipoServico() {
        return TipoServicoAdicional.PASSEIO_TURISTICO;
    }
}
