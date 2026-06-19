package com.marau.marau.tarifa;

import com.marau.marau.enums.TipoTarifa;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorTarifasTest {

    @Test
    void deveRetornarSempreAMesmaInstanciaSingleton() {
        GerenciadorTarifas primeiraInstancia = GerenciadorTarifas.getInstance();
        GerenciadorTarifas segundaInstancia = GerenciadorTarifas.getInstance();

        assertSame(primeiraInstancia, segundaInstancia);
    }

    @Test
    void deveCalcularTarifaPromocionalTemporaria() {
        ContextoTarifa contexto = new ContextoTarifa(
                200,
                LocalDate.of(2026, 9, 1),
                LocalDate.of(2026, 9, 4),
                TipoTarifa.PROMOCAO_TEMPORARIA);

        double valorTotal = GerenciadorTarifas.getInstance().calcularValorTotal(contexto);

        assertEquals(450, valorTotal);
    }

    @Test
    void deveListarEstrategiasCadastradas() {
        assertTrue(GerenciadorTarifas.getInstance().listarEstrategias().size() >= 7);
    }
}
