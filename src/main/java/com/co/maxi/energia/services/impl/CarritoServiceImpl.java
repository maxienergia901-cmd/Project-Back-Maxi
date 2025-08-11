package com.co.maxi.energia.services.impl;

import com.co.maxi.energia.Repository.CarritoRepository;
import com.co.maxi.energia.entity.Carrito;
import com.co.maxi.energia.services.CarritoService;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository repository;

    @Override
    public List<Carrito> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public List<Carrito> obtenerPorUsuario(final String usuario) {
        return repository.findByUsuarioId(usuario);
    }

    @Override
    public ResponseEntity<Carrito> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Carrito crear(final Carrito carrito) {
        List<Carrito> carritoUsuarios = repository.findByUsuarioId(carrito.getUsuarioId());

        for (Carrito carritoUpdate : carritoUsuarios) {
            carritoUpdate.setCreatedAt(carrito.getCreatedAt());
            repository.save(carritoUpdate);
        }

        return repository.save(carrito);
    }

    @Override
    public ResponseEntity<Carrito> actualizar(final Long id, final Carrito datos) {
        return repository.findById(id)
            .map(carrito -> {
                carrito.setNombre(datos.getNombre());
                carrito.setCantidad(datos.getCantidad());
                carrito.setColor(datos.getColor());
                carrito.setIdProducto(datos.getIdProducto());
                carrito.setPrecio(datos.getPrecio());
                carrito.setImagen(datos.getImagen());
                carrito.setPresentacion(datos.getPresentacion());
                carrito.setNombreColor(datos.getNombreColor());
                return ResponseEntity.ok(repository.save(carrito));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> eliminar(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @Scheduled(fixedRate = 1800000)
    public void borrarRegistrosVencidos() {
        LocalDateTime hace15Min = LocalDateTime.now().minusMinutes(30);
        List<Carrito> vencidos = repository.findByCreatedAtBefore(hace15Min);

        if (!vencidos.isEmpty()) {
            for (Carrito carrito : vencidos) {
                repository.deleteById(carrito.getId());
            }
        }
    }
}
