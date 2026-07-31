package com.taller.adapters.web.dto;

import com.taller.domain.interfaces.IServicio;

public record ServicioDTO(
    String id,
    String nombre,
    String precio) {

  public static ServicioDTO from(IServicio servicio) {
    return new ServicioDTO(servicio.getId(), servicio.getNombreDelServicio(), servicio.getPrecio().toString());
  }
}
