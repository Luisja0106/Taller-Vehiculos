package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.ListarOrdenesCMD;

public class ListarOrdenes {

  private final IOrdenRepository ordenRepository;

  public ListarOrdenes(IOrdenRepository ordenRepository) {
    this.ordenRepository = ordenRepository;
  }

  public Result<List<OrdenDeTrabajo>, IErrorApp> ejecutar(ListarOrdenesCMD input) {
    if (input == null) {
      return listarTodos();
    }
    EstadoDelTrabajo estado = null;
    if (input.estado() != null && !input.estado().isEmpty()) {
      var estadoOPT = EstadoDelTrabajo.buscarPorNombre(input.estado());
      if (estadoOPT.isEmpty()) {
        return Result.error(new VerificationError("Error estado invalido"));
      }
      estado = estadoOPT.get();
    }
    String empleado = null;
    if (input.empleadoId() != null && !input.empleadoId().isEmpty()) { // for verify that the empleado wouldn't be a
                                                                       // empty String
      empleado = input.empleadoId();
    }
    String vehiculo = null;
    if (input.placaVehiculo() != null && !input.placaVehiculo().isEmpty()) {
      vehiculo = input.placaVehiculo();
    }
    List<OrdenDeTrabajo> list = ordenRepository.listarConFiltros(estado, empleado, vehiculo);

    if (list == null) {
      return Result.error(new ActionError("Error al generar la lista con los datos solicitados"));
    }
    return Result.success(list);
  }

  private Result<List<OrdenDeTrabajo>, IErrorApp> listarTodos() {
    List<OrdenDeTrabajo> list = ordenRepository.listarTodos();

    if (list == null) {
      return Result.error(new ActionError("Error al obtener todas las ordenes"));
    }
    return Result.success(list);
  }
}
