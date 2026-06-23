package com.marau.marau.fidelidade;

import com.marau.marau.enums.CategoriaFidelidade;

/**
 * Benefício de desconto progressivo conforme a categoria do cliente.
 *
 *  BRONZE   →  5% de desconto
 *  PRATA    → 10% de desconto
 *  OURO     → 15% de desconto
 *  DIAMANTE → 20% de desconto
 */
public class BeneficioDescontoProgressivo implements BeneficioFidelidade {

    private final CategoriaFidelidade categoria;
    private final double percentualDesconto;

    public BeneficioDescontoProgressivo(CategoriaFidelidade categoria) {
        this.categoria = categoria;
        this.percentualDesconto = switch (categoria) {
            case BRONZE   -> 0.05;
            case PRATA    -> 0.10;
            case OURO     -> 0.15;
            case DIAMANTE -> 0.20;
        };
    }

    @Override
    public CategoriaFidelidade getCategoriaMinima() {
        return categoria;
    }

    @Override
    public String getDescricao() {
        int pct = (int) (percentualDesconto * 100);
        return String.format("Desconto de %d%% para categoria %s", pct, categoria.name());
    }

    @Override
    public double aplicar(double valorDiaria, int totalDiarias) {
        double total = valorDiaria * totalDiarias;
        return total * (1.0 - percentualDesconto);
    }
}
