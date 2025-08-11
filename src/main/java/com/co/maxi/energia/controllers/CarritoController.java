package com.co.maxi.energia.controllers;

import com.co.maxi.energia.entity.Carrito;
import com.co.maxi.energia.services.CarritoService;
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
@RequestMapping("maxienergia/api/carrito")
public class CarritoController {
    private final CarritoService service;

    @GetMapping("/obtener/todos")
    public List<Carrito> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/usuario/{idUsuario}")
    public List<Carrito> obtenerPorUsuario(@PathVariable String idUsuario) {
        return service.obtenerPorUsuario(idUsuario);
    }

    @GetMapping("/obtener/por/id/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Carrito crear(@RequestBody Carrito carrito) {
        return service.crear(carrito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizar(@PathVariable Long id, @RequestBody Carrito datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }
}
