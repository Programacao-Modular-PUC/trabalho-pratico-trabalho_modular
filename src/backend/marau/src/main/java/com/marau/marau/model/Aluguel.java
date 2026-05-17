package com.marau.marau.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    private double valorTotal;

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

    public double getValorTotal() {
        return valorTotal;
    }

    public void calcularValorTotal() {

        long dias =
                ChronoUnit.DAYS.between(
                        dataEntrada,
                        dataSaida);

        this.valorTotal =
                dias * quarto.getValorBase();
    }
}