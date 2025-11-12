package com.petstoreweb.petstore_backend.service;

import com.petstoreweb.petstore_backend.dto.ProveedorRequest;
import com.petstoreweb.petstore_backend.dto.ProveedorResponse;
import com.petstoreweb.petstore_backend.entity.Proveedor;
import com.petstoreweb.petstore_backend.repository.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaListarProveedores() {
        Proveedor proveedor1 = new Proveedor("Proveedor A", "123456", "a@test.com");
        proveedor1.setId(1);
        Proveedor proveedor2 = new Proveedor("Proveedor B", "789012", "b@test.com");
        proveedor2.setId(2);

        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor1, proveedor2));

        List<ProveedorResponse> proveedores = proveedorService.obtenerProveedores();

        assertThat(proveedores).hasSize(2);
        assertThat(proveedores.get(0).getNombre()).isEqualTo("Proveedor A");
        assertThat(proveedores.get(1).getNombre()).isEqualTo("Proveedor B");
        verify(proveedorRepository).findAll();
    }

    @Test
    void deberiaCrearProveedor() {
        ProveedorRequest request = new ProveedorRequest();
        request.setNombre("Nuevo proveedor");
        request.setTelefono("3001234567");
        request.setEmail("nuevo@proveedor.com");

        Proveedor guardado = new Proveedor("Nuevo proveedor", "3001234567", "nuevo@proveedor.com");
        guardado.setId(5);

        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(guardado);

        ProveedorResponse response = proveedorService.crearProveedor(request);

        assertThat(response.getId()).isEqualTo(5);
        assertThat(response.getNombre()).isEqualTo("Nuevo proveedor");

        ArgumentCaptor<Proveedor> captor = ArgumentCaptor.forClass(Proveedor.class);
        verify(proveedorRepository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("nuevo@proveedor.com");
    }

    @Test
    void deberiaActualizarProveedor() {
        Proveedor existente = new Proveedor("Proveedor existente", "111", "existente@test.com");
        existente.setId(7);

        ProveedorRequest request = new ProveedorRequest();
        request.setNombre("Proveedor actualizado");
        request.setTelefono("222");
        request.setEmail("actualizado@test.com");

        when(proveedorRepository.findById(7)).thenReturn(Optional.of(existente));
        when(proveedorRepository.save(any(Proveedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProveedorResponse response = proveedorService.actualizarProveedor(7, request);

        assertThat(response.getNombre()).isEqualTo("Proveedor actualizado");
        verify(proveedorRepository).save(existente);
    }

    @Test
    void deberiaLanzarErrorSiProveedorNoExiste() {
        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> proveedorService.obtenerProveedor(99));
    }
}

