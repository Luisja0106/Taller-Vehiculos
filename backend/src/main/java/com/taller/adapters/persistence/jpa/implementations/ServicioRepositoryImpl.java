package com.taller.adapters.persistence.jpa.implementations;

import java.util.List;
import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.ServicioJpaEntity;
import com.taller.adapters.persistence.jpa.repositories.ServicioJpaRepository;
import com.taller.domain.entities.Servicio;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;

public class ServicioRepositoryImpl implements IServicioRepository {

  private final ServicioJpaRepository jpaRepository;

  public ServicioRepositoryImpl(ServicioJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Result<Servicio, IErrorApp> guardar(Servicio servicio) {
    ServicioJpaEntity entity = new ServicioJpaEntity(servicio);
    jpaRepository.save(entity);
    return Result.success(servicio);
  }

  @Override
  public Result<Servicio, IErrorApp> actualizar(Servicio servicio) {
    ServicioJpaEntity entity = new ServicioJpaEntity(servicio);
    jpaRepository.save(entity);
    return Result.success(servicio);
  }

  @Override
  public Optional<Servicio> buscarPorId(String id) {
    return jpaRepository.findById(id)
        .map(ServicioJpaEntity::toDomain);
  }

  @Override
  public Optional<Servicio> buscarPorNombre(String nombre) {
    return jpaRepository.findByNombre(nombre)
        .map(ServicioJpaEntity::toDomain);
  }

  @Override
  public List<Servicio> listarTodos() {
    return jpaRepository.findAll().stream()
        .map(ServicioJpaEntity::toDomain)
        .toList();
  }

  @Override
  public void eliminar(String id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public int siguienteNumeroParaId() {
    return (int) jpaRepository.count() + 1;
  }
}
