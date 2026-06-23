package com.marau.marau.fidelidade;

import com.marau.marau.enums.CategoriaFidelidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gerenciador central do Programa de Fidelidade.
 *
 * <h2>Padrão: Singleton</h2>
 * <p>
 * Existe apenas uma instância desta classe em toda a aplicação. Isso garante
 * que as regras de categorias e os benefícios registrados sejam únicos e
 * consistentes em qualquer ponto do sistema que precise consultá-los — seja
 * um serviço de reservas, um controller REST ou um relatório gerencial.
 * Múltiplas instâncias poderiam levar a estados inconsistentes (ex.: um
 * serviço vendo benefícios diferentes de outro).
 * </p>
 *
 * <h2>Padrão: Strategy (via BeneficioFidelidade)</h2>
 * <p>
 * Cada benefício é uma estratégia independente. Novos benefícios podem ser
 * adicionados registrando uma nova implementação de {@link BeneficioFidelidade}
 * sem alterar esta classe.
 * </p>
 */
public final class GerenciadorFidelidade {

    // -------------------------------------------------------------------------
    // Singleton
    // -------------------------------------------------------------------------

    private static final GerenciadorFidelidade INSTANCE = new GerenciadorFidelidade();

    private GerenciadorFidelidade() {
        registrarBeneficios();
    }

    public static GerenciadorFidelidade getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------
    // Regras de categoria (quantas hospedagens para avançar)
    // -------------------------------------------------------------------------

    private static final int MINIMO_PRATA    = 3;
    private static final int MINIMO_OURO     = 6;
    private static final int MINIMO_DIAMANTE = 10;

    /**
     * Determina a categoria de fidelidade com base no total de hospedagens
     * concluídas pelo cliente.
     *
     * @param totalHospedagens número de hospedagens já realizadas (concluídas)
     * @return categoria correspondente
     */
    public CategoriaFidelidade determinarCategoria(int totalHospedagens) {
        if (totalHospedagens >= MINIMO_DIAMANTE) return CategoriaFidelidade.DIAMANTE;
        if (totalHospedagens >= MINIMO_OURO)     return CategoriaFidelidade.OURO;
        if (totalHospedagens >= MINIMO_PRATA)    return CategoriaFidelidade.PRATA;
        return CategoriaFidelidade.BRONZE;
    }

    // -------------------------------------------------------------------------
    // Registro e consulta de benefícios (Strategy)
    // -------------------------------------------------------------------------

    private final List<BeneficioFidelidade> beneficios = new ArrayList<>();

    private void registrarBeneficios() {
        // Desconto progressivo — disponível para todas as categorias
        beneficios.add(new BeneficioDescontoProgressivo(CategoriaFidelidade.BRONZE));
        beneficios.add(new BeneficioDescontoProgressivo(CategoriaFidelidade.PRATA));
        beneficios.add(new BeneficioDescontoProgressivo(CategoriaFidelidade.OURO));
        beneficios.add(new BeneficioDescontoProgressivo(CategoriaFidelidade.DIAMANTE));

        // Check-out estendido — a partir de PRATA
        beneficios.add(new BeneficioCheckoutEstendido());

        // Diária gratuita — OURO: 1 grátis a cada 5; DIAMANTE: 1 grátis a cada 3
        beneficios.add(new BeneficioDiariaGratuita(CategoriaFidelidade.OURO,     4));
        beneficios.add(new BeneficioDiariaGratuita(CategoriaFidelidade.DIAMANTE, 2));

        // Benefício exclusivo DIAMANTE
        beneficios.add(new BeneficioExclusivoDiamante());
    }

    /**
     * Permite registrar um novo benefício em tempo de execução.
     * Útil para testes ou extensões futuras sem recompilar o código.
     *
     * @param beneficio novo benefício a registrar
     */
    public synchronized void registrar(BeneficioFidelidade beneficio) {
        if (beneficio == null) {
            throw new IllegalArgumentException("Benefício não pode ser nulo.");
        }
        beneficios.add(beneficio);
    }

    /**
     * Retorna os benefícios disponíveis para uma determinada categoria.
     * Um benefício está disponível se sua {@code categoriaMinima} for ≤ à
     * categoria informada (pela ordem natural do enum).
     *
     * @param categoria categoria do cliente
     * @return lista imutável de benefícios aplicáveis
     */
    public List<BeneficioFidelidade> getBeneficiosDisponiveis(CategoriaFidelidade categoria) {
        List<BeneficioFidelidade> disponiveis = new ArrayList<>();
        for (BeneficioFidelidade b : beneficios) {
            if (b.getCategoriaMinima().ordinal() <= categoria.ordinal()) {
                disponiveis.add(b);
            }
        }
        return Collections.unmodifiableList(disponiveis);
    }

    /**
     * Retorna todos os benefícios cadastrados no programa.
     */
    public List<BeneficioFidelidade> getTodosBeneficios() {
        return Collections.unmodifiableList(new ArrayList<>(beneficios));
    }

    // -------------------------------------------------------------------------
    // Cálculo de valor com melhor benefício financeiro aplicado
    // -------------------------------------------------------------------------

    /**
     * Calcula o valor total da reserva aplicando o melhor benefício financeiro
     * disponível para a categoria do cliente.
     *
     * <p>Benefícios puramente operacionais (como check-out estendido) não
     * reduzem o valor e são informados separadamente via
     * {@link #getBeneficiosDisponiveis(CategoriaFidelidade)}.</p>
     *
     * @param valorDiaria      valor base da diária
     * @param totalDiarias     número de diárias da reserva
     * @param totalHospedagens total de hospedagens já realizadas pelo cliente
     * @return resultado com valor calculado e categoria
     */
    public ResultadoFidelidade calcular(double valorDiaria, int totalDiarias, int totalHospedagens) {
        if (valorDiaria <= 0)  throw new IllegalArgumentException("Valor da diária deve ser positivo.");
        if (totalDiarias <= 0) throw new IllegalArgumentException("Total de diárias deve ser positivo.");
        if (totalHospedagens < 0) throw new IllegalArgumentException("Total de hospedagens não pode ser negativo.");

        CategoriaFidelidade categoria = determinarCategoria(totalHospedagens);
        List<BeneficioFidelidade> disponiveis = getBeneficiosDisponiveis(categoria);

        // Aplica o benefício que resulta no MENOR valor final (melhor para o cliente)
        double valorSemBeneficio = valorDiaria * totalDiarias;
        double melhorValor = valorSemBeneficio;
        BeneficioFidelidade melhorBeneficio = null;

        for (BeneficioFidelidade b : disponiveis) {
            double valorComBeneficio = b.aplicar(valorDiaria, totalDiarias);
            if (valorComBeneficio < melhorValor) {
                melhorValor = valorComBeneficio;
                melhorBeneficio = b;
            }
        }

        return new ResultadoFidelidade(
                categoria,
                melhorValor,
                valorSemBeneficio,
                melhorBeneficio,
                disponiveis
        );
    }
}
