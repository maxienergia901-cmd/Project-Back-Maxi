package com.co.maxi.energia.services;

import com.co.maxi.energia.entity.Colores;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ColoresService {
    List<Colores> obtenerTodos();

    List<Colores> obtenerPorProducto(Long producto);

    ResponseEntity<Colores> obtenerPorId(Long id);

    Colores crear(Colores talla);

    ResponseEntity<Colores> actualizar(Long id, Colores datos);

    ResponseEntity<Void> eliminar(Long id);
}
