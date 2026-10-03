package com.taller.usecases;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;
import com.taller.usecases.dto.BuscarVehiculoPorPlacaCMD;

public class BuscarVehiculoPorPlaca {
  private final IVehiculoRepository repo;

  public BuscarVehiculoPorPlaca(IVehiculoRepository repo) {
    this.repo = repo;
  }

  public Result<Vehiculo, IErrorApp> ejecutar(BuscarVehiculoPorPlacaCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error no se puede realizar la busqueda con un input nulo"));
    }

    if (input.vehiculoPlaca() == null || input.vehiculoPlaca().isBlank()) {
      return Result.error(new ActionError("Error el id es nulo"));
    }

    var placa = Placa.crear(input.vehiculoPlaca());
    if (!placa.isSuccess) {
      return Result.error(new ActionError("Error la placa es invalida"));
    }
    var resu = repo.buscarPorPlaca(placa.getValue());

    if (resu.isEmpty()) {
      return Result.error(new ActionError("No se ha encontrado vehiculo con la placa ingresada"));
    }
    return Result.success(resu.get());
  }

}
