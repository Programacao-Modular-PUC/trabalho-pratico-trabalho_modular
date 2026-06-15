package com.marau.marau.repository;

import com.marau.marau.model.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
    List<Imovel> findByAtivoTrue();
    List<Imovel> findByCidadeContainingIgnoreCaseAndAtivoTrue(String cidade);
    List<Imovel> findByTipoContainingIgnoreCaseAndAtivoTrue(String tipo);
    List<Imovel> findByCidadeContainingIgnoreCaseAndTipoContainingIgnoreCaseAndAtivoTrue(String cidade, String tipo);
    List<Imovel> findByAnfitriaoId(Long anfitriaoId);
}
