package com.co.maxi.energia.services.impl;

import com.co.maxi.energia.dtos.Forms;
import com.co.maxi.energia.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FiltrosServiceImpl implements FiltrosService {
    private final CategoriaService categoriaService;
    private final PresentacionService presentacionService;
    private final ColoresService coloresService;

    @Override
    public ResponseEntity<Forms> obtenerPorProducto(final Long producto) {
        Forms filtros = new Forms();
        filtros.setCategorias(categoriaService.obtenerPorProducto(producto));
        filtros.setPresentacion(presentacionService.obtenerPorProducto(producto));
        filtros.setColores(coloresService.obtenerPorProducto(producto));

        if (filtros.getCategorias().isEmpty() ||
                filtros.getColores().isEmpty() ||
                filtros.getPresentacion().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(filtros);
    }
}
