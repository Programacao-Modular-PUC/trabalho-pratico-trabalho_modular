package com.marau.marau.repository;

import com.marau.marau.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    List<Aluguel> findByClienteId(Long clienteId);

    @Query("""
            select count(a) > 0 from Aluguel a
            where a.quarto.id = :quartoId
            and a.status <> 'CANCELADO'
            and :entrada < a.dataSaida
            and :saida > a.dataEntrada
            """)
    boolean existsAluguelAtivoNoPeriodo(
            @Param("quartoId") Long quartoId,
            @Param("entrada") LocalDate entrada,
            @Param("saida") LocalDate saida);
}
