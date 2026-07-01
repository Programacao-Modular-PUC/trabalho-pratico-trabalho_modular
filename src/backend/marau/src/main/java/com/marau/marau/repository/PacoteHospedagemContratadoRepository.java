package com.marau.marau.repository;

import com.marau.marau.model.PacoteHospedagemContratado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacoteHospedagemContratadoRepository extends JpaRepository<PacoteHospedagemContratado, Long> {

    List<PacoteHospedagemContratado> findByReservaId(Long reservaId);
}
