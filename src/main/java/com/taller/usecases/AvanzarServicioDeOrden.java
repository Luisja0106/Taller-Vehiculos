package com.taller.usecases;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.AvanzarEstadoDeOrdenInput;

public class AvanzarServicioDeOrden {

  private final IOrdenRepository ordenRepository;

  public AvanzarServicioDeOrden(IOrdenRepository ordenRepository) {
    this.ordenRepository = ordenRepository;
  }

  public Result<OrdenDeTrabajo, IErrorApp> ejecutar(AvanzarEstadoDeOrdenInput input) {
    if (input == null) {
      return Result.error(new ActionError("Error los datos no pueden ser nulos"));
    }
    var orden = ordenRepository.buscarPorId(input.ordenId());

    if (orden.isEmpty()) {
      return Result.error(new VerificationError("Error orden no encontrada"));
    }
    var result = orden.get().avanzarEstado();
    if (!result.isSuccess) {
      return Result.error(result.getError());
    }
    OrdenDeTrabajo ordenNueva = orden.get();

    ordenRepository.actualizar(ordenNueva);
    return Result.success(ordenNueva);
  }
}
