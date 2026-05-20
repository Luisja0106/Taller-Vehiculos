package com.taller.usecases;

import com.taller.domain.entities.Servicio;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;

public class RemoveServicio {
  private final IServicioRepository servicioRepo;
  private final IOrdenRepository ordenRepo;

  public RemoveServicio(IServicioRepository servicioRepo, IOrdenRepository ordenRepo) {
    this.servicioRepo = servicioRepo;
    this.ordenRepo = ordenRepo;
  }

  public Result<Void, IErrorApp> ejecutar(String servicioId) {

    if (servicioId == null || servicioId.isBlank()) {
      return Result.error(new VerificationError("Error el id no puede ser nulo"));
    }

    var resu = servicioRepo.buscarPorId(servicioId);
    if (resu.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro el servicio del id ingresado"));
    }
    var isUsed = ordenRepo.listarTodos().stream()
        .flatMap(o -> o.getServicios().stream())
        .anyMatch(s -> s.getId().equals(servicioId));

    if (isUsed) {
      return Result.error(new VerificationError(
          "El servicio esta siendo utilizado en almenos una orden, retirelo de las ordenes e intente de nuevo"));
    }

    servicioRepo.eliminar(servicioId);
    return Result.success(null);
  }
}
