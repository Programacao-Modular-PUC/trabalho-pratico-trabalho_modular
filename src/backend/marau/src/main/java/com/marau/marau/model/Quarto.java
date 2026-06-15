package com.marau.marau.model;

import com.marau.marau.enums.TipoQuarto;

import jakarta.persistence.*;

@Entity
@Table(name = "quartos")
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valorBase;

    private boolean possuiAR;

    private boolean possuiHidro;

    private int quantidadeCamas;

    private boolean possuiBerco;

    private int capacidadeHospedes;

    @Enumerated(EnumType.STRING)
    private TipoQuarto tipo;

    public Quarto() {
    }

    public Long getId() {
        return id;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public boolean isPossuiAR() {
        return possuiAR;
    }

    public void setPossuiAR(boolean possuiAR) {
        this.possuiAR = possuiAR;
    }

    public boolean isPossuiHidro() {
        return possuiHidro;
    }

    public void setPossuiHidro(boolean possuiHidro) {
        this.possuiHidro = possuiHidro;
    }

    public int getQuantidadeCamas() {
        return quantidadeCamas;
    }

    public void setQuantidadeCamas(int quantidadeCamas) {
        this.quantidadeCamas = quantidadeCamas;
    }

    public boolean isPossuiBerco() {
        return possuiBerco;
    }

    public void setPossuiBerco(boolean possuiBerco) {
        this.possuiBerco = possuiBerco;
    }

    public int getCapacidadeHospedes() {
        return capacidadeHospedes;
    }

    public void setCapacidadeHospedes(int capacidadeHospedes) {
        this.capacidadeHospedes = capacidadeHospedes;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }
}