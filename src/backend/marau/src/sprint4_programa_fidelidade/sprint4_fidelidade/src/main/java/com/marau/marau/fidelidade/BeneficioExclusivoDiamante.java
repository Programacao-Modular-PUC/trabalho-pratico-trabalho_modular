package com.marau.marau.fidelidade;

import com.marau.marau.enums.CategoriaFidelidade;

/**
 * Benefício exclusivo para clientes DIAMANTE: desconto adicional fixo de
 * R$ 50,00 por noite + kit de amenidades premium incluso.
 *
 * Demonstra como adicionar um benefício totalmente novo sem modificar
 * nenhuma classe existente.
 */
public class BeneficioExclusivoDiamante implements BeneficioFidelidade {

    private static final double DESCONTO_POR_NOITE = 50.0;

    @Override
    public CategoriaFidelidade getCategoriaMinima() {
        return CategoriaFidelidade.DIAMANTE;
    }

    @Override
    public String getDescricao() {
        return "Benefício exclusivo DIAMANTE: R$ 50,00 de desconto por noite + kit de amenidades premium";
    }

    @Override
    public double aplicar(double valorDiaria, int totalDiarias) {
        double valorComDesconto = Math.max(0, valorDiaria - DESCONTO_POR_NOITE);
        return valorComDesconto * totalDiarias;
    }
}
