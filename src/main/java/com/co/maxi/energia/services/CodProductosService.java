package com.co.maxi.energia.services;

import com.co.maxi.energia.entity.CodProductos;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CodProductosService {
    List<CodProductos> obtenerTodos();

    ResponseEntity<CodProductos> obtenerPorId(Long id);

    CodProductos crear(CodProductos codProducto);

    ResponseEntity<CodProductos> actualizar(Long id, CodProductos datos);

    ResponseEntity<Void> eliminar(Long id);
}
