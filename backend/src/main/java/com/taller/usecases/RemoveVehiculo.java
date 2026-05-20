package com.taller.usecases;

import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;

public class RemoveVehiculo {
  private final IVehiculoRepository vehiculoRepo;
  private final IOrdenRepository ordenRepo;

  public RemoveVehiculo(IVehiculoRepository vehiculoRepo, IOrdenRepository ordenRepo) {
    this.vehiculoRepo = vehiculoRepo;
    this.ordenRepo = ordenRepo;
  }

  public Result<Void, IErrorApp> ejecutar(String vehiculoPlaca) {
    if (vehiculoPlaca == null || vehiculoPlaca.isBlank()) {
      return Result.error(new VerificationError("Error el id es nulo"));
    }

    var placa = Placa.crear(vehiculoPlaca);

    if (!placa.isSuccess) {
      return Result.error(new VerificationError("Error la placa no es valida"));
    }
    var resu = vehiculoRepo.buscarPorPlaca(placa.getValue());
    if (resu.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro la placa ingresada"));
    }
    boolean isInOrden = ordenRepo.listarConFiltros(null, null, vehiculoPlaca).stream()
        .anyMatch(o -> o.getEstado() != EstadoDelTrabajo.FINALIZADO);

    if (isInOrden) {
      return Result.error(new VerificationError(
          "Error el vehiculo se encuentra en ordenes no finalizas, finalizelas o eliminelas y vuelva a intentar"));
    }
    vehiculoRepo.eliminar(placa.getValue());
    return Result.success(null);
  }
}
