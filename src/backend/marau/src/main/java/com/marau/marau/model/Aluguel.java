package com.marau.marau.model;

import com.marau.marau.enums.TipoTarifa;
import com.marau.marau.tarifa.ContextoTarifa;
import com.marau.marau.tarifa.GerenciadorTarifas;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "alugueis")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Quarto quarto;

    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private int quantidadeHospedes;
    private double valorTotal;
    private String status = "CONFIRMADO";

    @Enumerated(EnumType.STRING)
    private TipoTarifa tipoTarifa = TipoTarifa.PADRAO;

    public Aluguel() {
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public int getQuantidadeHospedes() {
        return quantidadeHospedes;
    }

    public void setQuantidadeHospedes(int quantidadeHospedes) {
        this.quantidadeHospedes = quantidadeHospedes;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(TipoTarifa tipoTarifa) {
        this.tipoTarifa = tipoTarifa == null ? TipoTarifa.PADRAO : tipoTarifa;
    }

    public void calcularValorTotal() {
        ContextoTarifa contexto = new ContextoTarifa(
                quarto.getValorBase(),
                dataEntrada,
                dataSaida,
                tipoTarifa);

        this.valorTotal = GerenciadorTarifas.getInstance().calcularValorTotal(contexto);
    }
}
