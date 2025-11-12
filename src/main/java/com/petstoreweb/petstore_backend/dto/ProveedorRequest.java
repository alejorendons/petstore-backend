package com.petstoreweb.petstore_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProveedorRequest {

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 50, message = "El nombre del proveedor no debe exceder los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El teléfono del proveedor es obligatorio")
    @Size(max = 50, message = "El teléfono del proveedor no debe exceder los 50 caracteres")
    private String telefono;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    @Size(max = 50, message = "El correo electrónico no debe exceder los 50 caracteres")
    private String email;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

