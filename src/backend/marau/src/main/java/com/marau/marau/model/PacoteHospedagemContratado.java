package com.marau.marau.model;

import com.marau.marau.enums.TipoPacoteHospedagem;
import com.marau.marau.enums.TipoServicoAdicional;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacotes_hospedagem")
public class PacoteHospedagemContratado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reserva reserva;

    @Enumerated(EnumType.STRING)
    private TipoPacoteHospedagem tipoPacote = TipoPacoteHospedagem.PERSONALIZADO;

    private double valorHospedagem;
    private double valorServicos;
    private double valorTotal;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "pacote_hospedagem_servicos",
            joinColumns = @JoinColumn(name = "pacote_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "servico")
    private List<TipoServicoAdicional> servicosAdicionais = new ArrayList<>();

    public PacoteHospedagemContratado() {
    }

    public Long getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public TipoPacoteHospedagem getTipoPacote() {
        return tipoPacote;
    }

    public void setTipoPacote(TipoPacoteHospedagem tipoPacote) {
        this.tipoPacote = tipoPacote == null ? TipoPacoteHospedagem.PERSONALIZADO : tipoPacote;
    }

    public double getValorHospedagem() {
        return valorHospedagem;
    }

    public void setValorHospedagem(double valorHospedagem) {
        this.valorHospedagem = valorHospedagem;
    }

    public double getValorServicos() {
        return valorServicos;
    }

    public void setValorServicos(double valorServicos) {
        this.valorServicos = valorServicos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<TipoServicoAdicional> getServicosAdicionais() {
        return servicosAdicionais;
    }

    public void setServicosAdicionais(List<TipoServicoAdicional> servicosAdicionais) {
        this.servicosAdicionais = servicosAdicionais == null ? new ArrayList<>() : servicosAdicionais;
    }
}
