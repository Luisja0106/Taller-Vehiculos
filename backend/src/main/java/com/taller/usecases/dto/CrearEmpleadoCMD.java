package com.taller.usecases.dto;

public record CrearEmpleadoCMD(
    String nombre,
    String telefono,
    String email,
    String rol,
    String contrato) {
}
