package com.co.maxi.energia.services;

import com.co.maxi.energia.dtos.Contactar;
import com.co.maxi.energia.entity.Pedidos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface PedidosService {
    List<Pedidos> obtenerTodos();

    Page<Pedidos> obtenerPorFiltro(Pageable pageable, Date fechaDesde, Date fechaHasta, Long estado);

    ResponseEntity<Pedidos> obtenerPorId(Long id);

    Pedidos crear(Pedidos pedido);

    Contactar contactar(Contactar contacto);

    ResponseEntity<Pedidos> actualizar(Long id, Pedidos datos);

    ResponseEntity<Void> eliminar(Long id);
}
