package com.co.maxi.energia.Repository;

import com.co.maxi.energia.entity.Colores;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColoresRepository extends JpaRepository<Colores, Long> {
    List<Colores> findByProductoId(Long productoId, final Sort orden);
}