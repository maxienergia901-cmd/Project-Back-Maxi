package com.co.maxi.energia.services;

import com.co.maxi.energia.entity.Productos;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductosService {
    List<Productos> obtenerTodos();

    Page<Productos> obtenerPorProducto(Long producto, Pageable pageable, Long categoriaId, Long presentacionId,
                                       Long precioInicio, Long precioFin, Long productoId);

    List<Productos> obtenerPrimerSlide(Long categoria1Id, Long categoria2Id, Long categoria3Id,
                                       Long categoria4Id);

    ResponseEntity<Productos> obtenerPorId(Long id);

    Productos crear(Productos producto);

    ResponseEntity<Productos> actualizar(Long id, Productos datos);

    ResponseEntity<Void> eliminar(Long id);
}
