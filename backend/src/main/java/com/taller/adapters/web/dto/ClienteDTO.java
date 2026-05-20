package com.taller.adapters.web.dto;

import java.util.List;

import com.taller.domain.entities.Cliente;
import com.taller.domain.entities.Vehiculo;

public record ClienteDTO(
    String id,
    String nombre,
    String telefono,
    String email,
    List<VehiculoDTO> vehiculos) {

  public static ClienteDTO from(Cliente cliente) {
    return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getTelefono().toString(),
        cliente.getEmail().toString(),
        cliente.getVehiculos().stream().map(v -> VehiculoDTO.from((Vehiculo) v)).toList());
  }
}
