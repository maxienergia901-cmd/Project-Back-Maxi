package com.co.maxi.energia.controllers;

import com.co.maxi.energia.entity.CodProductos;
import com.co.maxi.energia.services.CodProductosService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("maxienergia/api/codproductos")
public class CodProductosController {

    private final CodProductosService service;

    @GetMapping("/obtener/todos")
    public List<CodProductos> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<CodProductos> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public CodProductos crear(@RequestBody CodProductos codProducto) {
        return service.crear(codProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CodProductos> actualizar(@PathVariable Long id, @RequestBody CodProductos datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
