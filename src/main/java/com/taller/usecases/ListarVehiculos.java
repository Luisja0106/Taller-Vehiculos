package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.ListarVehiculosCMD;

public class ListarVehiculos {

  private final IVehiculoRepository vehiculoRepository;

  public ListarVehiculos(IVehiculoRepository vehiculoRepository) {
    this.vehiculoRepository = vehiculoRepository;
  }

  public Result<List<Vehiculo>, IErrorApp> ejecutar(ListarVehiculosCMD input) {
    if (input == null || input.clienteId() == null || input.clienteId().isEmpty()) {
      return listarTodos();
    }
    return listarPorCliente(input.clienteId());
  }

  private Result<List<Vehiculo>, IErrorApp> listarTodos() {
    List<Vehiculo> list = vehiculoRepository.listarTodos();

    if (list == null) {
      return Result.error(new ActionError("Error al obtener la lista de vehiculos"));
    }

    return Result.success(list);
  }

  private Result<List<Vehiculo>, IErrorApp> listarPorCliente(String clienteRaw) {
    if (clienteRaw == null || clienteRaw.isEmpty()) {
      return Result.error(new ActionError("Error para buscar por cliente, el cliente no puede ser nulo"));
    }
    List<Vehiculo> list = vehiculoRepository.listarPorCliente(clienteRaw);
    if (list == null) {
      return Result.error(new ActionError("Error al obtener los vehiculos del cliente ingresado"));
    }
    return Result.success(list);
  }

}
