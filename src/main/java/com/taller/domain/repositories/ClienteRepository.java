package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Empleado;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

public interface ClienteRepository {
  Result<Empleado, IErrorApp> guardar(Empleado empleado);

  Result<Empleado, IErrorApp> actualizar(Empleado empleado);

  Optional<Empleado> buscarPorId(String id);

  List<Empleado> listarTodos();

  void eliminar(String id);

  int siguienteNumeroId();
}
