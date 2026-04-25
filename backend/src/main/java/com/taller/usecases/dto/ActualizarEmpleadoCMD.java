package com.taller.usecases.dto;

public record ActualizarEmpleadoCMD(String idDelEmpleado,
    String nombre,
    String telefono,
    String email,
    String rol,
    String contrato) {
}
