package com.taller.usecases;

import com.taller.domain.entities.Servicio;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.BuscarServicioPorNombreCMD;

public class BuscarServicioPorNombre {

  private final IServicioRepository repo;

  public BuscarServicioPorNombre(IServicioRepository repo) {
    this.repo = repo;
  }

  public Result<Servicio, IErrorApp> ejecutar(BuscarServicioPorNombreCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error no se puede realizar la busqueda con un input nulo"));
    }
    if (input.nombre() == null || input.nombre().isBlank()) {
      return Result.error(new ActionError("Error el nombre es nulo"));
    }

    var result = repo.buscarPorNombre(input.nombre());

    if (result.isEmpty()) {
      return Result.error(new ActionError("Error no se encontro servicio con el nombre ingresado"));
    }

    return Result.success(result.get());
  }

}
