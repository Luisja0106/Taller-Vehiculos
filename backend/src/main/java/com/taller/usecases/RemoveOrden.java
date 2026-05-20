package com.taller.usecases;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;

public class RemoveOrden {
  private final IOrdenRepository ordenRepo;

  public RemoveOrden(IOrdenRepository ordenRepo) {
    this.ordenRepo = ordenRepo;
  }

  public Result<Void, IErrorApp> ejecutar(String ordenId) {
    if (ordenId == null || ordenId.isBlank()) {
      return Result.error(new VerificationError("Error el id no puede ser nulo"));
    }

    var resu = ordenRepo.buscarPorId(ordenId);
    if (resu.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro ninguna orden con el id ingresado"));
    }

    ordenRepo.eliminar(ordenId);
    return Result.success(null);
  }

}
