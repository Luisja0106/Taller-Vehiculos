package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.entities.Servicio;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.interfaces.IServicio;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.EliminarServicioDeOrdenCMD;

public class EliminarServicioDeOrden {

  private final IOrdenRepository ordenRepo;
  private final IServicioRepository servicioRepo;

  public EliminarServicioDeOrden(IOrdenRepository ordenRepo, IServicioRepository servicioRepo) {
    this.ordenRepo = ordenRepo;
    this.servicioRepo = servicioRepo;
  }

  public Result<OrdenDeTrabajo, IErrorApp> ejecutar(EliminarServicioDeOrdenCMD input) {
    if (input == null) {
      return Result.error(new VerificationError("Error el input no puede ser nulo"));
    }

    if (input.ordenId() == null) {
      return Result.error(new VerificationError("Error la orden no puede ser nula"));
    }

    if (input.ordenId().isBlank()) {
      return Result.error(new VerificationError("Error la orden no puede estar vacia"));
    }

    var ordenOPT = ordenRepo.buscarPorId(input.ordenId());

    if (ordenOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro ninguna orden con el id ingresado"));
    }

    if (input.servicioId() == null) {
      return Result.error(new VerificationError("Error el servicio no puede ser nulo"));
    }

    if (input.servicioId().isBlank()) {
      return Result.error(new VerificationError("Error el servicio no puede estar vacio"));
    }

    var servicioOPT = servicioRepo.buscarPorId(input.servicioId());

    if (servicioOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro ningun servicio con el id ingresado"));
    }

    OrdenDeTrabajo orden = ordenOPT.get();
    Servicio servicio = servicioOPT.get();

    List<IServicio> servicios = orden.getServicios();

    if (!servicios.contains(servicio)) {
      return Result.error(new VerificationError("Error la orden no cuenta con el servicio ingresado"));
    }

    orden.removerServicio(servicio);

    var guardado = ordenRepo.actualizar(orden);
    if (!guardado.isSuccess) {
      return Result.error(guardado.getError());
    }

    return Result.success(orden);
  }
}
