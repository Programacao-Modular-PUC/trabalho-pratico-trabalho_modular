package com.marau.marau.repository;

import com.marau.marau.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByImovelAnfitriaoId(Long anfitriaoId);
}
