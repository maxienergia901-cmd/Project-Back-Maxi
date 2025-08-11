package com.co.maxi.energia.services.impl;

import com.co.maxi.energia.Repository.PresentacionRepository;
import com.co.maxi.energia.entity.Presentacion;
import com.co.maxi.energia.services.PresentacionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PresentacionServiceImpl implements PresentacionService {

    private final PresentacionRepository repository;

    @Override public List<Presentacion> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public List<Presentacion> obtenerPorProducto(final Long producto) {
        return repository.findByProductoId(producto, Sort.by(Sort.Direction.ASC, "orden"));
    }

    @Override
    public ResponseEntity<Presentacion> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Presentacion crear(final Presentacion calidad) {
        return repository.save(calidad);
    }

    @Override
    public ResponseEntity<Presentacion> actualizar(final Long id, final Presentacion datos) {
        return repository.findById(id)
            .map(presentacion -> {
                presentacion.setNombre(datos.getNombre());
                presentacion.setOrden(datos.getOrden());
                return ResponseEntity.ok(repository.save(presentacion));
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

}
