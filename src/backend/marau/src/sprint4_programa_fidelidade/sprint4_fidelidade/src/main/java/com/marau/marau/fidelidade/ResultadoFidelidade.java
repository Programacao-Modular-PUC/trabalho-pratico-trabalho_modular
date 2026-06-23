package com.marau.marau.fidelidade;

import com.marau.marau.enums.CategoriaFidelidade;

import java.util.List;

/**
 * Objeto de resultado do cálculo do Programa de Fidelidade.
 *
 * Encapsula a categoria determinada, o valor final após benefício,
 * o valor original e a lista de benefícios disponíveis para o cliente.
 */
public class ResultadoFidelidade {

    private final CategoriaFidelidade categoria;
    private final double valorFinal;
    private final double valorOriginal;
    private final BeneficioFidelidade beneficioAplicado;
    private final List<BeneficioFidelidade> beneficiosDisponiveis;

    public ResultadoFidelidade(
            CategoriaFidelidade categoria,
            double valorFinal,
            double valorOriginal,
            BeneficioFidelidade beneficioAplicado,
            List<BeneficioFidelidade> beneficiosDisponiveis) {
        this.categoria = categoria;
        this.valorFinal = valorFinal;
        this.valorOriginal = valorOriginal;
        this.beneficioAplicado = beneficioAplicado;
        this.beneficiosDisponiveis = beneficiosDisponiveis;
    }

    public CategoriaFidelidade getCategoria() { return categoria; }

    public double getValorFinal() { return valorFinal; }

    public double getValorOriginal() { return valorOriginal; }

    /** Pode ser null caso nenhum benefício financeiro tenha sido aplicado. */
    public BeneficioFidelidade getBeneficioAplicado() { return beneficioAplicado; }

    public List<BeneficioFidelidade> getBeneficiosDisponiveis() { return beneficiosDisponiveis; }

    public double getEconomia() { return valorOriginal - valorFinal; }

    @Override
    public String toString() {
        return String.format(
                "ResultadoFidelidade{categoria=%s, valorOriginal=%.2f, valorFinal=%.2f, economia=%.2f, beneficio='%s'}",
                categoria,
                valorOriginal,
                valorFinal,
                getEconomia(),
                beneficioAplicado != null ? beneficioAplicado.getDescricao() : "nenhum"
        );
    }
}
