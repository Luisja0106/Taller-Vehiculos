package com.taller.usecases.dto;

public record CrearEmpleadoInput(
    String nombre,
    String telefono,
    String email,
    String rol,
    String contrato) {
}
