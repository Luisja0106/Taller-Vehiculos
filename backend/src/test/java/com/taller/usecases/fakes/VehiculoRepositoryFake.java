package com.taller.usecases.fakes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;

public class VehiculoRepositoryFake implements IVehiculoRepository {

  private final List<Vehiculo> vehiculos = new ArrayList<>();

  @Override
  public Result<Vehiculo, IErrorApp> guardar(Vehiculo vehiculo) {
    vehiculos.add(vehiculo);
    return Result.success(vehiculo);
  }

  @Override
  public Result<Vehiculo, IErrorApp> actualizar(Vehiculo vehiculo) {
    vehiculos.removeIf(v -> v.getPlaca().equals(vehiculo.getPlaca()));
    vehiculos.add(vehiculo);
    return Result.success(vehiculo);
  }

  @Override
  public Optional<Vehiculo> buscarPorPlaca(Placa placa) {
    return vehiculos.stream()
        .filter(v -> v.getPlaca().equals(placa))
        .findFirst();
  }

  @Override
  public List<Vehiculo> listarTodos() {
    return new ArrayList<>(vehiculos);
  }

  @Override
  public List<Vehiculo> listarPorCliente(String clienteId) {
    return vehiculos.stream()
        .filter(v -> v.getDueño().getId().equals(clienteId))
        .toList();
  }

  @Override
  public void eliminar(Placa placa) {
    vehiculos.removeIf(v -> v.getPlaca().equals(placa));
  }

}
