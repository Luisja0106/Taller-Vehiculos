package com.taller.usecases;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;
import com.taller.usecases.dto.ActualizarVehiculoCMD;

public class ActualizarVehiculo {

  private final IVehiculoRepository vehiculoRepo;
  private final IClienteRepository clienteRepo;

  public ActualizarVehiculo(IVehiculoRepository vehiculoRepo, IClienteRepository clienteRepo) {
    this.vehiculoRepo = vehiculoRepo;
    this.clienteRepo = clienteRepo;
  }

  public Result<Vehiculo, IErrorApp> ejecutar(ActualizarVehiculoCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error los datos a cambiar no puden ser nulos"));
    }
    var placaResult = Placa.crear(input.placaVehiculo());

    if (!placaResult.isSuccess) {
      return Result.error(placaResult.getError());
    }
    Placa placaVO = placaResult.getValue();
    var vehiculoOPT = vehiculoRepo.buscarPorPlaca(placaVO);

    if (vehiculoOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontron ningun vehiculo con la placa ingresada"));
    }
    Vehiculo vehiculo = vehiculoOPT.get();

    var modelo = cambiarModelo(vehiculo, input.modelo());
    if (!modelo.isSuccess) {
      return Result.error(modelo.getError());
    }

  }

  private Result<Void, IErrorApp> cambiarModelo(Vehiculo vehiculo, String modelo) {
    if (modelo == null || modelo.isEmpty()) {
      return Result.success(null);
    }
    vehiculo.setModelo(modelo);
    return Result.success(null);
  }

}
