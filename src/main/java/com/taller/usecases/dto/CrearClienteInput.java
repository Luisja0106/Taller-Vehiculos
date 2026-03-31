package com.taller.usecases.dto;

public record CrearClienteInput(String id,
    String nombre,
    String telefono,
    String email) {
}
