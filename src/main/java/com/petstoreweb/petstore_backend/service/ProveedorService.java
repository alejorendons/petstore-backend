package com.petstoreweb.petstore_backend.service;

import com.petstoreweb.petstore_backend.dto.ProveedorRequest;
import com.petstoreweb.petstore_backend.dto.ProveedorResponse;
import com.petstoreweb.petstore_backend.entity.Proveedor;
import com.petstoreweb.petstore_backend.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional(readOnly = true)
    public List<ProveedorResponse> obtenerProveedores() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProveedorResponse obtenerProveedor(Integer idProveedor) {
        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
        return mapearAResponse(proveedor);
    }

    @Transactional
    public ProveedorResponse crearProveedor(ProveedorRequest request) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(request.getNombre());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setEmail(request.getEmail());

        Proveedor guardado = proveedorRepository.save(proveedor);
        return mapearAResponse(guardado);
    }

    @Transactional
    public ProveedorResponse actualizarProveedor(Integer idProveedor, ProveedorRequest request) {
        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        proveedor.setNombre(request.getNombre());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setEmail(request.getEmail());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        return mapearAResponse(actualizado);
    }

    @Transactional
    public void eliminarProveedor(Integer idProveedor) {
        if (!proveedorRepository.existsById(idProveedor)) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }
        proveedorRepository.deleteById(idProveedor);
    }

    private ProveedorResponse mapearAResponse(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getTelefono(),
                proveedor.getEmail()
        );
    }
}

