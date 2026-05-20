package com.taller.usecases.dto;

public record ActualizarClienteCMD(
    String idDelCliente,
    String nombre,
    String telefono,
    String email) {
}
