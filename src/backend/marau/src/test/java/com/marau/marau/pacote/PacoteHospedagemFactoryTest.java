package com.marau.marau.pacote;

import com.marau.marau.enums.TipoPacoteHospedagem;
import com.marau.marau.enums.TipoServicoAdicional;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PacoteHospedagemFactoryTest {

    @Test
    void deveMontarPacotePersonalizadoComServicosLivres() {
        PacoteHospedagemFactory factory = new PacoteHospedagemFactory();

        PacoteHospedagem pacote = factory.montar(
                TipoPacoteHospedagem.PERSONALIZADO,
                500,
                List.of(
                        TipoServicoAdicional.CAFE_DA_MANHA,
                        TipoServicoAdicional.LAVANDERIA));

        assertEquals(575, pacote.getValorTotal());
        assertEquals(2, pacote.getServicosAdicionais().size());
        assertTrue(pacote.getServicosAdicionais().contains(TipoServicoAdicional.CAFE_DA_MANHA));
        assertTrue(pacote.getServicosAdicionais().contains(TipoServicoAdicional.LAVANDERIA));
    }

    @Test
    void deveMontarPacotePremiumComTodosOsServicosPrincipais() {
        PacoteHospedagemFactory factory = new PacoteHospedagemFactory();

        PacoteHospedagem pacote = factory.montar(
                TipoPacoteHospedagem.PREMIUM,
                1000,
                List.of());

        assertEquals(1375, pacote.getValorTotal());
        assertEquals(5, pacote.getServicosAdicionais().size());
    }

    @Test
    void naoDeveDuplicarServicoNoPacotePersonalizado() {
        PacoteHospedagemFactory factory = new PacoteHospedagemFactory();

        PacoteHospedagem pacote = factory.montar(
                TipoPacoteHospedagem.PERSONALIZADO,
                300,
                List.of(
                        TipoServicoAdicional.TRANSPORTE,
                        TipoServicoAdicional.TRANSPORTE));

        assertEquals(380, pacote.getValorTotal());
        assertEquals(1, pacote.getServicosAdicionais().size());
    }
}
