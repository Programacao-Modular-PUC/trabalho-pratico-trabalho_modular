package com.marau.marau.fidelidade;

import com.marau.marau.enums.CategoriaFidelidade;

/**
 * Interface Strategy para benefícios do Programa de Fidelidade.
 *
 * Cada benefício concreto implementa esta interface, permitindo a criação de
 * novos benefícios sem modificar o código existente (Open/Closed Principle).
 *
 * Padrão utilizado: Strategy
 */
public interface BeneficioFidelidade {

    /**
     * Retorna a categoria mínima para este benefício ser aplicado.
     */
    CategoriaFidelidade getCategoriaMinima();

    /**
     * Descrição legível do benefício para exibição ao cliente.
     */
    String getDescricao();

    /**
     * Aplica o benefício sobre o valor base da diária e retorna o valor ajustado.
     *
     * @param valorDiaria  valor original da diária
     * @param totalDiarias total de diárias da reserva
     * @return             valor total após aplicação do benefício
     */
    double aplicar(double valorDiaria, int totalDiarias);
}
