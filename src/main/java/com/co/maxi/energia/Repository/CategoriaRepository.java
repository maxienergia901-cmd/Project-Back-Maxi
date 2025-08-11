package com.co.maxi.energia.Repository;

import com.co.maxi.energia.entity.Categoria;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByProductoId(Long productoId, final Sort orden);
}
