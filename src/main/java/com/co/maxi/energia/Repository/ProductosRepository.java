package com.co.maxi.energia.Repository;

import com.co.maxi.energia.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductosRepository extends JpaRepository<Productos, Long>, JpaSpecificationExecutor<Productos> {
}
