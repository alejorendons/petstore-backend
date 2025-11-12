package com.petstoreweb.petstore_backend.controller;

import com.petstoreweb.petstore_backend.dto.ProveedorRequest;
import com.petstoreweb.petstore_backend.dto.ProveedorResponse;
import com.petstoreweb.petstore_backend.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> listarProveedores() {
        return ResponseEntity.ok(proveedorService.obtenerProveedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> obtenerProveedor(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.obtenerProveedor(id));
    }

    @PostMapping
    public ResponseEntity<ProveedorResponse> crearProveedor(@Valid @RequestBody ProveedorRequest request) {
        ProveedorResponse response = proveedorService.crearProveedor(request);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponse> actualizarProveedor(@PathVariable Integer id,
                                                                 @Valid @RequestBody ProveedorRequest request) {
        ProveedorResponse response = proveedorService.actualizarProveedor(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Integer id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}

