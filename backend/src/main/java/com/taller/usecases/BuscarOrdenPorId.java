package com.taller.usecases;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.BuscarOrdenPorIdCMD;

public class BuscarOrdenPorId {

  private final IOrdenRepository repo;

  public BuscarOrdenPorId(IOrdenRepository repo) {
    this.repo = repo;
  }

  public Result<OrdenDeTrabajo, IErrorApp> ejecutar(BuscarOrdenPorIdCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error el input no puede ser nulo"));
    }

    if (input.ordenId().isBlank()) {
      return Result.error(new ActionError("Error el id es nulo"));
    }

    var resu = repo.buscarPorId(input.ordenId());

    if (resu.isEmpty()) {
      return Result.error(new ActionError("No se encontro Orden con el id ingresado"));
    }

    return Result.success(resu.get());
  }

}
