package com.co.maxi.energia.Repository;

import java.util.List;

import com.co.maxi.energia.entity.Presentacion;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentacionRepository extends JpaRepository<Presentacion, Long> {
    List<Presentacion> findByProductoId(Long productoId, final Sort orden);
}
