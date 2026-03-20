package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

public interface OrdenRepository {
  Result<OrdenDeTrabajo, IErrorApp> guardar(OrdenDeTrabajo orden);

  Result<OrdenDeTrabajo, IErrorApp> actualizar(OrdenDeTrabajo orden);

  Optional<OrdenDeTrabajo> buscarPorId(String id);

  List<OrdenDeTrabajo> listarTodos();

  List<OrdenDeTrabajo> listarPorEstados(EstadoDelTrabajo estado);

  List<OrdenDeTrabajo> listarPorVehiculo(String placa);

  List<OrdenDeTrabajo> listarPorEmpleado(String empleadoId);

  void eliminar(String id);

  int siguienteNumeroParaId();
}
