package com.taller.usecases;

import com.taller.domain.entities.Cliente;
import com.taller.domain.entities.Vehiculo;
import com.taller.domain.enums.Marca;
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
    var marca = cambiarMarca(vehiculo, input.marca());
    if (!marca.isSuccess) {
      return Result.error(marca.getError());
    }

    var anio = cambiarAnio(vehiculo, input.año());
    if (!anio.isSuccess) {
      return Result.error(anio.getError());
    }

    var dueño = cambiarDueño(vehiculo, input.nuevoDueñoId());

    if (!dueño.isSuccess) {
      return Result.error(dueño.getError());
    }
    vehiculoRepo.actualizar(vehiculo);
    return Result.success(vehiculo);
  }

  private Result<Void, IErrorApp> cambiarModelo(Vehiculo vehiculo, String modelo) {
    if (modelo == null || modelo.isEmpty()) {
      return Result.success(null);
    }
    vehiculo.setModelo(modelo);
    return Result.success(null);
  }

  private Result<Void, IErrorApp> cambiarMarca(Vehiculo vehiculo, String marcaRaw) {
    if (marcaRaw == null || marcaRaw.isBlank()) {
      return Result.success(null);
    }

    var marcaOPT = Marca.buscarPorNombre(marcaRaw);

    if (marcaOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se pudo encontrar esa marca"));
    }

    vehiculo.setMarca(marcaOPT.get());
    return Result.success(null);
  }

  private Result<Void, IErrorApp> cambiarAnio(Vehiculo vehiculo, String anio) {
    if (anio == null || anio.isBlank()) {
      return Result.success(null);
    }

    try {
      int anioInt = Integer.parseInt(anio);

      vehiculo.setAnio(anioInt);
      return Result.success(null);
    } catch (NumberFormatException e) {
      return Result.error(new VerificationError("El valor ingresado para el año es invalido"));
    }
  }

  private Result<Void, IErrorApp> cambiarDueño(Vehiculo vehiculo, String nuevoDueñoId) {
    if (nuevoDueñoId == null || nuevoDueñoId.isBlank()) {
      return Result.success(null);
    }
    var nuevoDueñoOPT = clienteRepo.buscarPorId(nuevoDueñoId);

    if (nuevoDueñoOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro el cliente con ese id"));
    }

    Cliente nuevoDueño = nuevoDueñoOPT.get();
    Cliente antiguoDueño = vehiculo.getDueño();
    vehiculo.cambiarDueño(nuevoDueño);

    clienteRepo.actualizar(nuevoDueño);
    if (antiguoDueño != null) {
      clienteRepo.actualizar(antiguoDueño);
    }

    return Result.success(null);
  }

}
