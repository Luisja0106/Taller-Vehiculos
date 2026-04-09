package com.taller.usecases.dto;

public record ActualizarVehiculoCMD(String placaVehiculo, String nuevoDueñoId, String modelo, String marca,
    String año) {
}
