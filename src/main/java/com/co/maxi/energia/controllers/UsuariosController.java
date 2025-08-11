package com.co.maxi.energia.controllers;

import com.co.maxi.energia.dtos.InicioSesion;
import com.co.maxi.energia.entity.Usuarios;
import com.co.maxi.energia.services.UsuariosService;
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
@RequestMapping("maxienergia/api/usuarios")
public class UsuariosController {

    private final UsuariosService service;

    @GetMapping("/obtener/todos")
    public List<Usuarios> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/obtener/por/email/{email}")
    public Usuarios obtenerPorProducto(@PathVariable String email) {
        return service.obtenerPorEmail(email);
    }

    @PostMapping
    public Usuarios crear(@RequestBody Usuarios usuario) {
        return service.crear(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> actualizar(@PathVariable Long id, @RequestBody Usuarios datos) {
        return service.actualizar(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InicioSesion request) {
        return service.login(request);
    }
}
