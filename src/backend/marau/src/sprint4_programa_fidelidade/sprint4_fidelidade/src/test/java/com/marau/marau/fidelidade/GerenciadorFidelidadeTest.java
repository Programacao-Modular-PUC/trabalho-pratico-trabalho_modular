package com.marau.marau.fidelidade;

import com.marau.marau.enums.CategoriaFidelidade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do Programa de Fidelidade.
 *
 * Valida: Singleton, determinação de categorias, benefícios e cálculo de valor.
 */
class GerenciadorFidelidadeTest {

    private final GerenciadorFidelidade gerenciador = GerenciadorFidelidade.getInstance();

    // -------------------------------------------------------------------------
    // Singleton
    // -------------------------------------------------------------------------

    @Test
    void deveRetornarSempreAMesmaInstancia() {
        GerenciadorFidelidade instancia1 = GerenciadorFidelidade.getInstance();
        GerenciadorFidelidade instancia2 = GerenciadorFidelidade.getInstance();
        assertSame(instancia1, instancia2, "Deve ser a mesma instância (Singleton)");
    }

    // -------------------------------------------------------------------------
    // Determinação de categorias
    // -------------------------------------------------------------------------

    @Test
    void deveRetornarBronzeParaZeroHospedagens() {
        assertEquals(CategoriaFidelidade.BRONZE, gerenciador.determinarCategoria(0));
    }

    @Test
    void deveRetornarBronzeParaUmaHospedagem() {
        assertEquals(CategoriaFidelidade.BRONZE, gerenciador.determinarCategoria(1));
    }

    @Test
    void deveRetornarPrataAPartirDe3Hospedagens() {
        assertEquals(CategoriaFidelidade.PRATA, gerenciador.determinarCategoria(3));
        assertEquals(CategoriaFidelidade.PRATA, gerenciador.determinarCategoria(5));
    }

    @Test
    void deveRetornarOuroAPartirDe6Hospedagens() {
        assertEquals(CategoriaFidelidade.OURO, gerenciador.determinarCategoria(6));
        assertEquals(CategoriaFidelidade.OURO, gerenciador.determinarCategoria(9));
    }

    @Test
    void deveRetornarDiamanteAPartirDe10Hospedagens() {
        assertEquals(CategoriaFidelidade.DIAMANTE, gerenciador.determinarCategoria(10));
        assertEquals(CategoriaFidelidade.DIAMANTE, gerenciador.determinarCategoria(50));
    }

    // -------------------------------------------------------------------------
    // Benefícios disponíveis por categoria
    // -------------------------------------------------------------------------

    @Test
    void bronzeDeveTerApenasDescontoProgressivo() {
        List<BeneficioFidelidade> beneficios = gerenciador.getBeneficiosDisponiveis(CategoriaFidelidade.BRONZE);
        assertFalse(beneficios.isEmpty(), "BRONZE deve ter ao menos 1 benefício");
        // Nenhum benefício de PRATA, OURO ou DIAMANTE deve aparecer
        beneficios.forEach(b ->
                assertEquals(CategoriaFidelidade.BRONZE, b.getCategoriaMinima(),
                        "BRONZE só deve ver benefícios de nível BRONZE"));
    }

    @Test
    void prataDeveriaIncluirBeneficiosDeBronzeEPrata() {
        List<BeneficioFidelidade> beneficios = gerenciador.getBeneficiosDisponiveis(CategoriaFidelidade.PRATA);
        assertTrue(beneficios.size() >= 2, "PRATA deve ter benefícios de BRONZE e PRATA");
    }

    @Test
    void diamanteDeveVerTodosOsBeneficios() {
        List<BeneficioFidelidade> todos = gerenciador.getTodosBeneficios();
        List<BeneficioFidelidade> diamante = gerenciador.getBeneficiosDisponiveis(CategoriaFidelidade.DIAMANTE);
        assertEquals(todos.size(), diamante.size(), "DIAMANTE deve ver todos os benefícios");
    }

    // -------------------------------------------------------------------------
    // Cálculo de valor
    // -------------------------------------------------------------------------

    @Test
    void bronzeDeveAplicar5PorCentoDeDesconto() {
        // 0 hospedagens → BRONZE → desconto de 5%
        ResultadoFidelidade resultado = gerenciador.calcular(200.0, 3, 0);
        assertEquals(CategoriaFidelidade.BRONZE, resultado.getCategoria());
        double esperado = 200.0 * 3 * 0.95;
        assertEquals(esperado, resultado.getValorFinal(), 0.01);
    }

    @Test
    void prataDeveAplicar10PorCentoDeDesconto() {
        // 3 hospedagens → PRATA → desconto de 10%
        ResultadoFidelidade resultado = gerenciador.calcular(200.0, 3, 3);
        assertEquals(CategoriaFidelidade.PRATA, resultado.getCategoria());
        double esperado = 200.0 * 3 * 0.90;
        assertEquals(esperado, resultado.getValorFinal(), 0.01);
    }

    @Test
    void diamanteDeveAplicarMelhorBeneficio() {
        // 10 hospedagens → DIAMANTE
        // Valor por diária = R$ 300,00, 3 diárias
        ResultadoFidelidade resultado = gerenciador.calcular(300.0, 3, 10);
        assertEquals(CategoriaFidelidade.DIAMANTE, resultado.getCategoria());
        assertTrue(resultado.getValorFinal() < resultado.getValorOriginal(),
                "Valor final deve ser menor que o original");
        assertTrue(resultado.getEconomia() > 0, "Deve haver economia");
    }

    @Test
    void deveLancarExcecaoParaValorDiariaNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> gerenciador.calcular(-100.0, 3, 5));
    }

    @Test
    void deveLancarExcecaoParaTotalDiariasZero() {
        assertThrows(IllegalArgumentException.class,
                () -> gerenciador.calcular(200.0, 0, 5));
    }

    // -------------------------------------------------------------------------
    // Benefícios individuais
    // -------------------------------------------------------------------------

    @Test
    void descontoProgressivoBronzeDeveSerCorreto() {
        BeneficioDescontoProgressivo b = new BeneficioDescontoProgressivo(CategoriaFidelidade.BRONZE);
        double resultado = b.aplicar(100.0, 5);
        assertEquals(475.0, resultado, 0.01); // 500 - 5%
    }

    @Test
    void diariaGratuitaDeveAbaterCorretamente() {
        // intervalo=4: a cada grupo de 5 diárias, 1 é grátis
        BeneficioDiariaGratuita b = new BeneficioDiariaGratuita(CategoriaFidelidade.OURO, 4);
        // 5 diárias → 1 grátis, paga 4
        assertEquals(400.0, b.aplicar(100.0, 5), 0.01);
        // 10 diárias → 2 grátis, paga 8
        assertEquals(800.0, b.aplicar(100.0, 10), 0.01);
    }

    @Test
    void beneficioExclusivoDiamanteDeveAplicarDescontoFixo() {
        BeneficioExclusivoDiamante b = new BeneficioExclusivoDiamante();
        // R$ 200/noite - R$ 50 = R$ 150/noite * 3 noites = R$ 450
        assertEquals(450.0, b.aplicar(200.0, 3), 0.01);
    }
}
