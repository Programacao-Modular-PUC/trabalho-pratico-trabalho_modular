package com.marau.marau.repository;

import com.marau.marau.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository
        extends JpaRepository<Cliente, Long> {
}