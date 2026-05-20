package com.taller.usecases.dto;

public record CrearVehiculoCMD(String placa,
    String id_cliente,
    String modelo,
    String marca,
    int anio) {
}
