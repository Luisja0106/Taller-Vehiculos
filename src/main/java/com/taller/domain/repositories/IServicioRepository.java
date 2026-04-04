package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Servicio;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

public interface IServicioRepository {

  Result<Servicio, IErrorApp> guardar(Servicio servicio);

  Result<Servicio, IErrorApp> actualizar(Servicio servicio);

  Optional<Servicio> buscarPorId(String id);

  Optional<Servicio> buscarPorNombre(String nombre);

  List<Servicio> listarTodos();

  void eliminar(String id);

  int siguienteNumeroParaId();

}
