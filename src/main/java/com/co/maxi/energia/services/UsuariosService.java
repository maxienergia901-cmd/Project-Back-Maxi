package com.co.maxi.energia.services;

import com.co.maxi.energia.dtos.InicioSesion;

import java.util.List;

import com.co.maxi.energia.entity.Usuarios;
import org.springframework.http.ResponseEntity;

public interface UsuariosService {
    List<Usuarios> obtenerTodos();

    Usuarios obtenerPorEmail(String email);

    Usuarios crear(Usuarios usuario);

    ResponseEntity<Usuarios> actualizar(Long id, Usuarios datos);

    ResponseEntity<Void> eliminar(Long id);

    ResponseEntity<?> login(InicioSesion request);
}
