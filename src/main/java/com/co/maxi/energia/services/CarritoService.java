package com.co.maxi.energia.services;

import com.co.maxi.energia.entity.Carrito;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CarritoService {
    List<Carrito> obtenerTodos();

    List<Carrito> obtenerPorUsuario(String usuario);

    ResponseEntity<Carrito> obtenerPorId(Long id);

    Carrito crear(Carrito carrito);

    ResponseEntity<Carrito> actualizar(Long id, Carrito datos);

    ResponseEntity<Void> eliminar(Long id);
}
