package com.taller.adapters.web.dto;

import com.taller.domain.entities.Vehiculo;

public record VehiculoDTO(
    String placa,
    String dueñoId,
    String modelo,
    String marca,
    int anio) {

  public static VehiculoDTO from(Vehiculo vehiculo) {
    return new VehiculoDTO(vehiculo.getPlaca().getValue(), vehiculo.getDueño().getId(), vehiculo.getModelo(),
        vehiculo.getMarca(), vehiculo.getAnio());
  }
}
