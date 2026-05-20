package com.taller.adapters.web.dto;

import com.taller.domain.entities.Empleado;

public record EmpleadoDTO(
    String id,
    String nombre,
    String email,
    String telefono,
    String rol,
    String contrato) {

  public static EmpleadoDTO from(Empleado empleado) {
    return new EmpleadoDTO(empleado.getId(), empleado.getNombre(), empleado.getEmail().toString(),
        empleado.getTelefono().toString(), empleado.getRol().toString(), empleado.getContrato().toString());
  }
}
