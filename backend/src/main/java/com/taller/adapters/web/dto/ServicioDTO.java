package com.taller.adapters.web.dto;

import com.taller.domain.entities.Servicio;

public record ServicioDTO(
    String id,
    String nombre,
    String precio) {

  public static ServicioDTO from(Servicio servicio) {
    return new ServicioDTO(servicio.getId(), servicio.getNombreDelServicio(), servicio.getPrecio().toString());
  }
}
