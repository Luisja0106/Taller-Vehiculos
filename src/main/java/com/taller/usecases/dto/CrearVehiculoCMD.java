package com.taller.usecases.dto;

public record CrearVehiculoCMD(String placa,
    String idCliente,
    String modelo,
    String marca,
    int anio) {
}
