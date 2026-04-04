package com.taller.usecases;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.AgregarServicioCMD;

public class AgregarServicio {
  private final IOrdenRepository ordenRepository;
  private final IServicioRepository servicioRepository;

  public AgregarServicio(IServicioRepository servicioRepository, IOrdenRepository ordenRepository) {
    this.servicioRepository = servicioRepository;
    this.ordenRepository = ordenRepository;
  }

  public Result<OrdenDeTrabajo, IErrorApp> ejecutar(AgregarServicioCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error los datos no pueden ser nulos"));
    }
    var orden = ordenRepository.buscarPorId(input.ordenId());
    if (orden.isEmpty()) {
      return Result.error(new VerificationError("Error no se pudo encontrar la orden de trabajo"));
    }
    var service = servicioRepository.buscarPorId(input.servicioId());
    if (service.isEmpty()) {
      return Result.error(new VerificationError("Error no se pudo encontrar el servicio especificado"));
    }
    var result = orden.get().addServicio(service.get());
    if (!result.isSuccess) {
      return Result.error(result.getError());
    }
    OrdenDeTrabajo ordenActualizada = orden.get();
    ordenRepository.actualizar(ordenActualizada);

    return Result.success(ordenActualizada);
  }
}
