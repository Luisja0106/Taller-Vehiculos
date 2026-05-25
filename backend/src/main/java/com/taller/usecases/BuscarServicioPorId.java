package com.taller.usecases;

import com.taller.domain.entities.Servicio;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.BuscarServicioPorIdCMD;

public class BuscarServicioPorId {

  private final IServicioRepository repo;

  public BuscarServicioPorId(IServicioRepository repo) {
    this.repo = repo;
  }

  public Result<Servicio, IErrorApp> ejecutar(BuscarServicioPorIdCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error no se puede realizar la busqueda con un input nulo"));
    }
    if (input.servicioId().isBlank()) {
      return Result.error(new ActionError("Error el id es nulo"));
    }

    var result = repo.buscarPorId(input.servicioId());

    if (result.isEmpty()) {
      return Result.error(new ActionError("Error no se encontro servicio con el id ingresado"));
    }

    return Result.success(result.get());
  }

}
