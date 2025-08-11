package com.co.maxi.energia.Repository;

import com.co.maxi.energia.entity.Carrito;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByUsuarioId(String usuarioId);

    List<Carrito> findByCreatedAtBefore(LocalDateTime createdAt);
}
