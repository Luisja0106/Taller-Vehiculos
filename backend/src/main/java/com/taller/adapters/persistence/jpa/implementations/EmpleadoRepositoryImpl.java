package com.taller.adapters.persistence.jpa.implementations;

import java.util.List;
import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.EmpleadoJpaEntity;
import com.taller.adapters.persistence.jpa.repositories.EmpleadoJpaRepository;
import com.taller.domain.entities.Empleado;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.utils.Result;

import org.springframework.stereotype.Repository;

@Repository
public class EmpleadoRepositoryImpl implements IEmpleadoRepository {

  private final EmpleadoJpaRepository jpaRepo;

  public EmpleadoRepositoryImpl(EmpleadoJpaRepository jpaRepo) {
    this.jpaRepo = jpaRepo;
  }

  @Override
  public Result<Empleado, IErrorApp> guardar(Empleado empleado) {
    EmpleadoJpaEntity entity = new EmpleadoJpaEntity(empleado);
    jpaRepo.save(entity);
    return Result.success(empleado);
  }

  @Override
  public Result<Empleado, IErrorApp> actualizar(Empleado empleado) {
    EmpleadoJpaEntity entity = new EmpleadoJpaEntity(empleado);
    jpaRepo.save(entity);
    return Result.success(empleado);
  }

  @Override
  public Optional<Empleado> buscarPorId(String id) {
    return jpaRepo.findById(id)
        .map(EmpleadoJpaEntity::toDomain);
  }

  @Override
  public Optional<Empleado> buscarPorEmail(String email) {
    return jpaRepo.findByEmail(email)
        .map(EmpleadoJpaEntity::toDomain);

  }

  @Override
  public List<Empleado> listarTodos() {
    return jpaRepo.findAll().stream()
        .map(EmpleadoJpaEntity::toDomain)
        .toList();
  }

  @Override
  public void eliminar(String id) {
    jpaRepo.deleteById(id);
  }

  @Override
  public int siguienteNumeroId() {
    return (int) jpaRepo.count() + 1;
  }

}
