package com.co.maxi.energia.services.impl;

import com.co.maxi.energia.Repository.ProductosRepository;
import com.co.maxi.energia.entity.Productos;
import com.co.maxi.energia.services.FileMetadataService;
import com.co.maxi.energia.services.ProductosService;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductosServiceImpl implements ProductosService {
    private final ProductosRepository repository;

    private final FileMetadataService fileMetadataService;

    @Override
    public List<Productos> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Page<Productos> obtenerPorProducto(final Long producto, Pageable pageable, Long categoriaId,
                                              Long presentacionId, Long precioInicio, Long precioFin, Long productoId) {

        Specification<Productos> spec = Specification.where((root,
                                                             query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("producto").get("id"), producto)
        );

        if (categoriaId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("categoria").get("id"), categoriaId));
        }

        if (presentacionId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("presentacion").get("id"), presentacionId));
        }

        if (productoId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("id"), productoId));
        }

        if (precioInicio != null && precioFin != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("precio"), precioInicio, precioFin));
        }

        return repository.findAll(spec, pageable);
    }

    @Override
    public List<Productos> obtenerPrimerSlide(Long categoria1Id, Long categoria2Id, Long categoria3Id,
                                        Long categoria4Id) {

        List<Productos> result = new ArrayList<>();
        Pageable topTwo = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "id"));

        Function<Long, Specification<Productos>> specForCategory = (catId) -> {
            if (catId == null) return null;
            return (root, query, cb)
                    -> cb.equal(root.get("categoria").get("id"), catId);
        };

        List<Long> categorias = Arrays.asList(categoria1Id, categoria2Id, categoria3Id, categoria4Id);

        for (Long catId : categorias) {
            if (catId == null) continue;
            Specification<Productos> spec = specForCategory.apply(catId);
            Page<Productos> page = repository.findAll(spec, topTwo);
            if (page != null && !page.isEmpty()) {
                result.addAll(page.getContent());
            }
        }

        return result;
    }

    @Override
    public ResponseEntity<Productos> obtenerPorId(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Productos crear(final Productos producto) {
        return repository.save(producto);
    }

    @Override
    public ResponseEntity<Productos> actualizar(final Long id, final Productos datos) {
        return repository.findById(id)
            .map(productos -> {
                productos.setNombre(datos.getNombre());
                productos.setDescripcion(datos.getDescripcion());
                productos.setPrecio(datos.getPrecio());
                productos.setPresentacion(datos.getPresentacion());
                productos.setCategoria(datos.getCategoria());
                productos.setColores(datos.getColores());
                return ResponseEntity.ok(repository.save(productos));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<Void> eliminar(final Long id) {
        if (repository.existsById(id)) {
            Productos producto = repository.findById(id).get();

            repository.deleteById(id);

            if(producto.getImagen() != null) {
                fileMetadataService.deleteArchivo(producto.getImagen().getId());
            }

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
