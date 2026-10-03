package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.ListarOrdenesActivasCMD;

public class ListarOrdenesActivas {

  private final IOrdenRepository ordenRepository;

  public ListarOrdenesActivas(IOrdenRepository ordenRepository) {
    this.ordenRepository = ordenRepository;
  }

  public Result<List<OrdenDeTrabajo>, IErrorApp> ejecutar(ListarOrdenesActivasCMD input) {
    List<OrdenDeTrabajo> list;
    if (input == null) {
      list = ordenRepository.listarOrdenesActivas(null, null);
    } else {
      String empleado = null;
      if (input.empleadoId() != null && !input.empleadoId().isBlank()) {
        empleado = input.empleadoId().trim();
      }
      String vehiculo = null;
      if (input.placaVehiculo() != null && !input.placaVehiculo().isBlank()) {
        vehiculo = input.placaVehiculo().trim();
      }
      list = ordenRepository.listarOrdenesActivas(empleado, vehiculo);
    }

    if (list == null) {
      return Result.error(new ActionError("Error al generar la lista con los datos solicitados"));
    }
    return Result.success(list);
  }
}
