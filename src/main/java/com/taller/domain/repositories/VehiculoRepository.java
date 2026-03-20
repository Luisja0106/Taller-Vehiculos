package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;

public interface VehiculoRepository {

  Result<Vehiculo, IErrorApp> guardar(Vehiculo vehiculo);

  Result<Vehiculo, IErrorApp> actualizar(Vehiculo vehiculo);

  Optional<Vehiculo> buscarPorPlaca(Placa placa);

  List<Vehiculo> listarTodos();

  List<Vehiculo> listarPorCliente(String clienteId);

  void eliminar(Placa placa);
}
