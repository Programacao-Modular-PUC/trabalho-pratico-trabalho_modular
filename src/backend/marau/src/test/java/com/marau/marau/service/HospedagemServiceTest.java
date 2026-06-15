package com.marau.marau.service;

import com.marau.marau.enums.TipoQuarto;
import com.marau.marau.exception.CapacidadeExcedidaException;
import com.marau.marau.exception.DataInvalidaException;
import com.marau.marau.exception.QuartoIndisponivelException;
import com.marau.marau.exception.RecursoNaoPermitidoException;
import com.marau.marau.model.Quarto;
import com.marau.marau.repository.AluguelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HospedagemServiceTest {

    private AluguelRepository aluguelRepository;
    private HospedagemService service;

    @BeforeEach
    void setUp() {
        aluguelRepository = mock(AluguelRepository.class);
        service = new HospedagemService(aluguelRepository);
    }

    @Test
    void deveCalcularDiariaDoQuartoIndividual() {
        Quarto quarto = new Quarto();
        quarto.setTipo(TipoQuarto.INDIVIDUAL);
        quarto.setValorBase(150);
        quarto.setQuantidadeCamas(1);
        quarto.setPossuiBerco(false);

        Quarto resultado = service.aplicarRegrasDoQuarto(quarto);

        assertEquals(150, resultado.getValorBase());
        assertEquals(1, resultado.getCapacidadeHospedes());
    }

    @Test
    void deveCalcularDiariaDoQuartoDuploComBerco() {
        Quarto quarto = new Quarto();
        quarto.setTipo(TipoQuarto.DUPLO);
        quarto.setValorBase(200);
        quarto.setPossuiBerco(true);

        Quarto resultado = service.aplicarRegrasDoQuarto(quarto);

        assertEquals(280, resultado.getValorBase());
        assertEquals(2, resultado.getCapacidadeHospedes());
    }

    @Test
    void deveCalcularDiariaDoQuartoFamilia() {
        Quarto quarto = new Quarto();
        quarto.setTipo(TipoQuarto.FAMILIA);
        quarto.setValorBase(300);
        quarto.setCapacidadeHospedes(4);

        Quarto resultado = service.aplicarRegrasDoQuarto(quarto);

        assertEquals(480, resultado.getValorBase());
    }

    @Test
    void naoDevePermitirBercoEmQuartoIndividual() {
        Quarto quarto = new Quarto();
        quarto.setTipo(TipoQuarto.INDIVIDUAL);
        quarto.setValorBase(150);
        quarto.setQuantidadeCamas(1);
        quarto.setPossuiBerco(true);

        assertThrows(RecursoNaoPermitidoException.class, () -> service.aplicarRegrasDoQuarto(quarto));
    }

    @Test
    void naoDevePermitirHospedesAcimaDaCapacidade() {
        assertThrows(CapacidadeExcedidaException.class, () -> service.validarCapacidade(5, 2));
    }

    @Test
    void naoDevePermitirDataSaidaAntesOuIgualEntrada() {
        LocalDate entrada = LocalDate.of(2026, 6, 20);
        LocalDate saida = LocalDate.of(2026, 6, 20);

        assertThrows(DataInvalidaException.class, () -> service.validarDatas(entrada, saida));
    }

    @Test
    void naoDevePermitirQuartoIndisponivel() {
        LocalDate entrada = LocalDate.of(2026, 6, 20);
        LocalDate saida = LocalDate.of(2026, 6, 22);
        when(aluguelRepository.existsAluguelAtivoNoPeriodo(1L, entrada, saida)).thenReturn(true);

        assertThrows(QuartoIndisponivelException.class, () -> service.validarDisponibilidade(1L, entrada, saida));
    }

    @Test
    void devePermitirQuartoDisponivel() {
        LocalDate entrada = LocalDate.of(2026, 6, 20);
        LocalDate saida = LocalDate.of(2026, 6, 22);
        when(aluguelRepository.existsAluguelAtivoNoPeriodo(1L, entrada, saida)).thenReturn(false);

        assertDoesNotThrow(() -> service.validarDisponibilidade(1L, entrada, saida));
    }
}
