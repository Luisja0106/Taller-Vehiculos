package com.taller.usecases.dto;

public record crearVehiculoInput(String placa,
    String idCliente,
    String modelo,
    String marca,
    int anio) {
}
