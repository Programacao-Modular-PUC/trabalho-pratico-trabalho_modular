package com.marau.marau.dto;

public class AluguelResponseDTO {

    private Long id;
    private double valorTotal;

    public AluguelResponseDTO(
            Long id,
            double valorTotal) {

        this.id = id;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}