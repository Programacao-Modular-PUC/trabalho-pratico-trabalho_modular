package com.marau.marau.fidelidade;

import com.marau.marau.enums.CategoriaFidelidade;

/**
 * Benefício de diária gratuita: a cada {@code intervalo} diárias pagas,
 * o cliente ganha 1 diária de graça.
 *
 *  OURO     → 1 diária grátis a cada 5 diárias
 *  DIAMANTE → 1 diária grátis a cada 3 diárias
 */
public class BeneficioDiariaGratuita implements BeneficioFidelidade {

    private final CategoriaFidelidade categoriaMinima;
    private final int intervalo; // diárias pagas para ganhar 1 grátis

    public BeneficioDiariaGratuita(CategoriaFidelidade categoriaMinima, int intervalo) {
        this.categoriaMinima = categoriaMinima;
        this.intervalo = intervalo;
    }

    @Override
    public CategoriaFidelidade getCategoriaMinima() {
        return categoriaMinima;
    }

    @Override
    public String getDescricao() {
        return String.format(
                "1 diária grátis a cada %d diárias (mínimo: %s)",
                intervalo, categoriaMinima.name());
    }

    @Override
    public double aplicar(double valorDiaria, int totalDiarias) {
        int diariasGratis = totalDiarias / (intervalo + 1);
        int diariasPagas = totalDiarias - diariasGratis;
        return valorDiaria * diariasPagas;
    }
}
