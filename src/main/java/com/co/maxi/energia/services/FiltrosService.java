package com.co.maxi.energia.services;

import com.co.maxi.energia.dtos.Forms;
import org.springframework.http.ResponseEntity;

public interface FiltrosService {
    ResponseEntity<Forms> obtenerPorProducto(Long producto);
}
