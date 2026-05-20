package com.taller.usecases;

import com.taller.domain.entities.Empleado;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.BuscarEmpleadoPorIdCMD;

public class BuscarEmpleadoPorId {

  private final IEmpleadoRepository repo;

  public BuscarEmpleadoPorId(IEmpleadoRepository repo) {
    this.repo = repo;
  }

  public Result<Empleado, IErrorApp> ejecutar(BuscarEmpleadoPorIdCMD input) {
    if (input == null) {
      return Result.error(new ActionError("El input no puede ser nulo"));
    }

    if (input.empleadoId().isBlank()) {
      return Result.error(new ActionError("No se puede buscar con el id null"));
    }

    var result = repo.buscarPorId(input.empleadoId());

    if (result.isEmpty()) {
      return Result.error(new ActionError("No se encontro el empleado con el id ingresado"));
    }

    return Result.success(result.get());
  }

}
