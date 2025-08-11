package com.co.maxi.energia.controllers;

import com.co.maxi.energia.entity.Categoria;
import com.co.maxi.energia.services.CategoriaService;
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
@RequestMapping("maxienergia/api/categoria")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping("/obtener/todos")
    public List<Categoria> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/producto/{producto}")
    public List<Categoria> obtenerPorProducto(@PathVariable Long producto) {
        return service.obtenerPorProducto(producto);
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria) {
        return service.crear(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
