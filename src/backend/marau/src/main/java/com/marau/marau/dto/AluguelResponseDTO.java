package com.marau.marau.dto;

import com.marau.marau.enums.TipoTarifa;

public class AluguelResponseDTO {

    private Long id;
    private double valorTotal;
    private TipoTarifa tipoTarifa;

    public AluguelResponseDTO(
            Long id,
            double valorTotal) {

        this(id, valorTotal, TipoTarifa.PADRAO);
    }

    public AluguelResponseDTO(
            Long id,
            double valorTotal,
            TipoTarifa tipoTarifa) {

        this.id = id;
        this.valorTotal = valorTotal;
        this.tipoTarifa = tipoTarifa == null ? TipoTarifa.PADRAO : tipoTarifa;
    }

    public Long getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }
}
