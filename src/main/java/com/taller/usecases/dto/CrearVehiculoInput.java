package com.taller.usecases.dto;

public record CrearVehiculoInput(String placa,
    String idCliente,
    String modelo,
    String marca,
    int anio) {
}
