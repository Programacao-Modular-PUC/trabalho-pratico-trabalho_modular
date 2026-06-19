package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public final class GerenciadorTarifas {

    private static final GerenciadorTarifas INSTANCE = new GerenciadorTarifas();

    private final Map<TipoTarifa, EstrategiaTarifa> estrategias =
            new EnumMap<>(TipoTarifa.class);

    private GerenciadorTarifas() {
        registrar(new TarifaPadrao());
        registrar(new TarifaAltaTemporada());
        registrar(new TarifaBaixaTemporada());
        registrar(new TarifaFeriado());
        registrar(new TarifaEventoEspecial());
        registrar(new TarifaPromocaoTemporaria());
        registrar(new TarifaClienteFrequente());
    }

    public static GerenciadorTarifas getInstance() {
        return INSTANCE;
    }

    public synchronized void registrar(EstrategiaTarifa estrategia) {
        if (estrategia == null) {
            throw new IllegalArgumentException("Estrategia de tarifa nao informada.");
        }
        estrategias.put(estrategia.getTipo(), estrategia);
    }

    public double calcularValorTotal(ContextoTarifa contexto) {
        if (contexto == null) {
            throw new IllegalArgumentException("Contexto de tarifa nao informado.");
        }

        EstrategiaTarifa estrategia = estrategias.get(contexto.getTipoTarifa());
        if (estrategia == null) {
            throw new NoSuchElementException("Tipo de tarifa nao cadastrado.");
        }

        return arredondar(estrategia.calcular(contexto));
    }

    public List<EstrategiaTarifa> listarEstrategias() {
        return new ArrayList<>(estrategias.values());
    }

    private double arredondar(double valor) {
        return BigDecimal.valueOf(valor)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
