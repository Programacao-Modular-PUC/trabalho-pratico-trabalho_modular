package com.marau.marau.repository;

import com.marau.marau.model.ProgramaFidelidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório JPA para a entidade {@link ProgramaFidelidade}.
 */
public interface ProgramaFidelidadeRepository extends JpaRepository<ProgramaFidelidade, Long> {

    Optional<ProgramaFidelidade> findByClienteId(Long clienteId);

    boolean existsByClienteId(Long clienteId);
}
