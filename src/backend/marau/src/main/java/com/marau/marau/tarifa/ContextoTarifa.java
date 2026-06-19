package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ContextoTarifa {

    private final double valorDiariaBase;
    private final LocalDate dataEntrada;
    private final LocalDate dataSaida;
    private final TipoTarifa tipoTarifa;

    public ContextoTarifa(
            double valorDiariaBase,
            LocalDate dataEntrada,
            LocalDate dataSaida,
            TipoTarifa tipoTarifa) {

        if (valorDiariaBase <= 0) {
            throw new IllegalArgumentException("Valor base da diaria precisa ser maior que zero.");
        }
        if (dataEntrada == null || dataSaida == null) {
            throw new IllegalArgumentException("Data de entrada e saida sao obrigatorias.");
        }
        if (!dataSaida.isAfter(dataEntrada)) {
            throw new IllegalArgumentException("A data de saida precisa ser depois da data de entrada.");
        }

        this.valorDiariaBase = valorDiariaBase;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.tipoTarifa = tipoTarifa == null ? TipoTarifa.PADRAO : tipoTarifa;
    }

    public double getValorDiariaBase() {
        return valorDiariaBase;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }

    public long getQuantidadeDiarias() {
        long diarias = ChronoUnit.DAYS.between(dataEntrada, dataSaida);
        return Math.max(1, diarias);
    }
}
