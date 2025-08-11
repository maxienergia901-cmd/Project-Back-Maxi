package com.co.maxi.energia.services;

import com.co.maxi.energia.entity.Presentacion;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface PresentacionService {

    List<Presentacion> obtenerTodos();

    List<Presentacion> obtenerPorProducto(Long producto);

    ResponseEntity<Presentacion> obtenerPorId(Long id);

    Presentacion crear(Presentacion calidad);

    ResponseEntity<Presentacion> actualizar(Long id, Presentacion datos);

    ResponseEntity<Void> eliminar(Long id);
}
