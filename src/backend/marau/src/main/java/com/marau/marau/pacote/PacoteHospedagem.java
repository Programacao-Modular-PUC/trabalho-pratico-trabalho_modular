package com.marau.marau.pacote;

import com.marau.marau.enums.TipoServicoAdicional;

import java.util.List;

public interface PacoteHospedagem {

    String getNome();

    double getValorHospedagem();

    double getValorTotal();

    List<TipoServicoAdicional> getServicosAdicionais();

    List<String> getDescricoesServicos();
}
