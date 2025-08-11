package com.co.maxi.energia.Repository;

import com.co.maxi.energia.entity.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PedidosRepository extends JpaRepository<Pedidos, Long>, JpaSpecificationExecutor<Pedidos> {
}