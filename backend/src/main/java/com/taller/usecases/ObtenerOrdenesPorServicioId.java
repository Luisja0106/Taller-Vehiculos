package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;

public class ObtenerOrdenesPorServicioId {
  private final IOrdenRepository ordenRepo;
  private final IServicioRepository servicioRepo;

  public ObtenerOrdenesPorServicioId(IOrdenRepository ordenRepo, IServicioRepository servicioRepo) {
    this.ordenRepo = ordenRepo;
    this.servicioRepo = servicioRepo;
  }

  public Result<List<OrdenDeTrabajo>, IErrorApp> ejecutar(String servicioId) {
    if (servicioId == null) {
      return Result.error(new VerificationError("Error el id del servicio no puede ser nulo"));
    }
    var servicio = servicioRepo.buscarPorId(servicioId);

    if (servicio.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro ningun servicio con el id ingresado"));
    }

    List<OrdenDeTrabajo> resu = ordenRepo.obtenerOrdenesPorServicioId(servicioId);

    return Result.success(resu);
  }
}
