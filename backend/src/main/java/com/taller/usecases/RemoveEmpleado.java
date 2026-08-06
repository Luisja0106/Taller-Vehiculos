package com.taller.usecases;

import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;

public class RemoveEmpleado {

  private final IEmpleadoRepository empleadoRepo;
  private final IOrdenRepository ordenRepo;

  public RemoveEmpleado(IEmpleadoRepository empleadoRepo, IOrdenRepository ordenRepo) {
    this.empleadoRepo = empleadoRepo;
    this.ordenRepo = ordenRepo;
  }

  public Result<Void, IErrorApp> ejecutar(String empleadoId) {
    if (empleadoId == null || empleadoId.isBlank()) {
      return Result.error(new VerificationError("Error el id es nulo"));
    }
    var resu = empleadoRepo.buscarPorId(empleadoId);

    if (resu.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro empleado con el id ingresado"));
    }

    var haveOrdens = ordenRepo.listarOrdenesActivas(empleadoId, null);
    if (!haveOrdens.isEmpty()) {
      return Result.error(new VerificationError(
          "Error el empleado cuenta con ordenes activas, favor finalizar las ordenes o asignar un nuevo empleado a cargo en  ellas "));
    }
    empleadoRepo.eliminar(empleadoId);
    return Result.success(null);
  }
}
