package com.marau.marau.repository;

import com.marau.marau.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AluguelRepository
        extends JpaRepository<Aluguel, Long> {
}