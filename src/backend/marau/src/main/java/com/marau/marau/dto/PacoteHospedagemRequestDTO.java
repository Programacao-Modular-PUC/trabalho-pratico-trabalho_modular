package com.marau.marau.dto;

import com.marau.marau.enums.TipoPacoteHospedagem;
import com.marau.marau.enums.TipoServicoAdicional;

import java.util.ArrayList;
import java.util.List;

public class PacoteHospedagemRequestDTO {

    private Long reservaId;
    private double valorHospedagemBase;
    private TipoPacoteHospedagem tipoPacote = TipoPacoteHospedagem.PERSONALIZADO;
    private List<TipoServicoAdicional> servicosAdicionais = new ArrayList<>();

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public double getValorHospedagemBase() {
        return valorHospedagemBase;
    }

    public void setValorHospedagemBase(double valorHospedagemBase) {
        this.valorHospedagemBase = valorHospedagemBase;
    }

    public TipoPacoteHospedagem getTipoPacote() {
        return tipoPacote;
    }

    public void setTipoPacote(TipoPacoteHospedagem tipoPacote) {
        this.tipoPacote = tipoPacote == null ? TipoPacoteHospedagem.PERSONALIZADO : tipoPacote;
    }

    public List<TipoServicoAdicional> getServicosAdicionais() {
        return servicosAdicionais;
    }

    public void setServicosAdicionais(List<TipoServicoAdicional> servicosAdicionais) {
        this.servicosAdicionais = servicosAdicionais == null ? new ArrayList<>() : servicosAdicionais;
    }
}
