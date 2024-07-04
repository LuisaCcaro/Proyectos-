package com.Mascoshop.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Mascoshop.Entidades.Compra;
import com.Mascoshop.Servicios.CompraService;
import com.Mascoshop.dto.CompraDTO;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCompra(@RequestBody CompraDTO compraDTO) {
        try {
            Compra nuevaCompra = compraService.crearCompra(compraDTO);
            return new ResponseEntity<>(nuevaCompra, HttpStatus.CREATED);
        } catch (Exception e) {
            // Devolver un mensaje de error claro
            return new ResponseEntity<>("Error al crear la compra: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
