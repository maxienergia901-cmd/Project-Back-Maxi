package com.co.maxi.energia.services.impl;

import com.co.maxi.energia.Repository.ColoresRepository;
import com.co.maxi.energia.entity.Colores;
import com.co.maxi.energia.services.ColoresService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColoresServiceImpl implements ColoresService {

    private final ColoresRepository repository;

    @Override
    public List<Colores> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public List<Colores> obtenerPorProducto(final Long producto) {
        return repository.findByProductoId(producto, Sort.by(Sort.Direction.ASC, "orden"));
    }

    @Override
    public ResponseEntity<Colores> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Colores crear(final Colores color) {
        return repository.save(color);
    }

    @Override
    public ResponseEntity<Colores> actualizar(final Long id, final Colores datos) {
        return repository.findById(id)
            .map(color -> {
                color.setNombre(datos.getNombre());
                color.setCodigo(datos.getCodigo());
                color.setOrden(datos.getOrden());
                return ResponseEntity.ok(repository.save(color));
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
