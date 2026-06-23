package com.marau.marau.fidelidade;

import com.marau.marau.enums.CategoriaFidelidade;

/**
 * Benefício de check-out estendido disponível para clientes PRATA ou superior.
 *
 * Não altera o valor financeiro — o benefício é operacional (o cliente pode
 * sair do quarto mais tarde no dia de check-out). Retorna o valor original
 * sem modificação.
 */
public class BeneficioCheckoutEstendido implements BeneficioFidelidade {

    @Override
    public CategoriaFidelidade getCategoriaMinima() {
        return CategoriaFidelidade.PRATA;
    }

    @Override
    public String getDescricao() {
        return "Check-out estendido até as 14h (categoria PRATA ou superior)";
    }

    @Override
    public double aplicar(double valorDiaria, int totalDiarias) {
        // Benefício operacional — sem desconto financeiro
        return valorDiaria * totalDiarias;
    }
}
