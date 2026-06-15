package com.marau.marau.repository;

import com.marau.marau.enums.TipoQuarto;
import com.marau.marau.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartoRepository
        extends JpaRepository<Quarto, Long> {

    List<Quarto> findByTipo(TipoQuarto tipo);
}
