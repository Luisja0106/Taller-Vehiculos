package com.taller.usecases;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.enums.Marca;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;
import com.taller.usecases.dto.CrearVehiculoCMD;

public class RegistrarVehiculo {

  private final IVehiculoRepository vehiculoRepository;
  private final IClienteRepository clienteRepository;

  public RegistrarVehiculo(IVehiculoRepository vehiculoRepository, IClienteRepository clienteRepository) {
    this.vehiculoRepository = vehiculoRepository;
    this.clienteRepository = clienteRepository;
  }

  public Result<Vehiculo, IErrorApp> ejecutar(CrearVehiculoCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Los datos no pueden ser nulos"));
    }
    var placa = Placa.crear(input.placa());
    if (!placa.isSuccess) {
      return Result.error(placa.getError());
    }
    if (vehiculoRepository.buscarPorPlaca(placa.getValue()).isPresent()) {
      return Result.error(new ActionError("Placa ya registrada"));
    }
    var marca = Marca.buscarPorNombre(input.marca());
    if (marca.isEmpty()) {
      return Result.error(new VerificationError("Marca invalida"));
    }
    var cliente = clienteRepository.buscarPorId(input.idCliente());
    if (cliente.isEmpty()) {
      return Result.error(new VerificationError("Cliente no encontrado"));
    }
    var vehiculoResult = Vehiculo.crear(input.placa(), cliente.get(), input.modelo(), marca.get(), input.anio());

    if (!vehiculoResult.isSuccess) {
      return vehiculoResult;
    }
    Vehiculo nuevoVehiculo = vehiculoResult.getValue();
    cliente.get().addVehiculo(nuevoVehiculo);

    clienteRepository.actualizar(cliente.get());
    vehiculoRepository.guardar(nuevoVehiculo);
    return Result.success(nuevoVehiculo);
  }

}
