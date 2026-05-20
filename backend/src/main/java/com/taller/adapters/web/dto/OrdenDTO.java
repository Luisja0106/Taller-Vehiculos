package com.taller.adapters.web.dto;

import java.util.List;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.entities.Servicio;

public record OrdenDTO(
    String id,
    String vehiculoPlaca,
    String vehiculoModelo,
    String vehiculoMarca,
    int vehiculoAnio,
    String empleadoId,
    String empleadoNombre,
    String clienteId,
    String clienteNombre,
    String estado,
    String fechaEntrada,
    String fechaDeFinalizacion,
    String fechaDePago,
    String valorVenta,
    List<ServicioDTO> servicios) {

  public static OrdenDTO from(OrdenDeTrabajo orden) {
    return new OrdenDTO(orden.getID(), orden.getVehiculo().getPlaca().getValue(), orden.getVehiculo().getModelo(),
        orden.getVehiculo().getMarca(), orden.getVehiculo().getAnio(), orden.getEmpleadoACargo().getId(),
        orden.getEmpleadoACargo().getNombre(), orden.getVehiculo().getDueño().getId(),
        orden.getVehiculo().getDueño().getNombre(), orden.getEstado().toString(),
        orden.getFechaEntrada() != null ? orden.getFechaEntrada().toString() : null,
        orden.getFechaDeFinalizacion() != null ? orden.getFechaDeFinalizacion().toString() : null,
        orden.getFechaDePago() != null ? orden.getFechaDePago().toString() : null,
        orden.getValorVenta() != null ? orden.getValorVenta().toString() : null,
        orden.getServicios().stream()
            .map(s -> ServicioDTO.from((Servicio) s)).toList());
  }
}
