package com.taller.usecases.fakes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Servicio;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;

public class ServicioRepositoryFake implements IServicioRepository {

  private final List<Servicio> servicios = new ArrayList<>();
  private int contador = 0;

  @Override
  public Result<Servicio, IErrorApp> guardar(Servicio servicio) {
    servicios.add(servicio);
    return Result.success(servicio);
  }

  @Override
  public Result<Servicio, IErrorApp> actualizar(Servicio servicio) {
    servicios.removeIf(s -> s.getId().equals(servicio.getId()));
    servicios.add(servicio);
    return Result.success(servicio);
  }

  @Override
  public Optional<Servicio> buscarPorId(String id) {
    return servicios.stream()
        .filter(s -> s.getId().equals(id))
        .findFirst();
  }

  @Override
  public Optional<Servicio> buscarPorNombre(String nombre) {
    return servicios.stream()
        .filter(s -> s.getNombreDelServicio().equals(nombre))
        .findFirst();
  }

  @Override
  public List<Servicio> listarTodos() {
    return new ArrayList<>(servicios);
  }

  @Override
  public void eliminar(String id) {
    servicios.removeIf(s -> s.getId().equals(id));
  }

  @Override
  public int siguienteNumeroParaId() {
    return ++contador;
  }

}
