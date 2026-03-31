package com.taller.usecases.dto;

public record CrearClienteInput(
    String nombre,
    String telefono,
    String email) {
}
