package com.taller.adapters.persistence.jpa.implementations;

import java.util.List;
import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.VehiculoJpaEntity;
import com.taller.adapters.persistence.jpa.repositories.VehiculoJpaRepository;
import com.taller.domain.entities.Vehiculo;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public class VehiculoRepositoryImpl implements IVehiculoRepository {

  private final VehiculoJpaRepository jpaRepo;

  public VehiculoRepositoryImpl(VehiculoJpaRepository jpaRepo) {
    this.jpaRepo = jpaRepo;
  }

  @Override
  public Result<Vehiculo, IErrorApp> guardar(Vehiculo vehiculo) {
    VehiculoJpaEntity entity = new VehiculoJpaEntity(vehiculo);
    jpaRepo.save(entity);
    return Result.success(vehiculo);
  }

  @Override
  public Result<Vehiculo, IErrorApp> actualizar(Vehiculo vehiculo) {
    VehiculoJpaEntity entity = new VehiculoJpaEntity(vehiculo);
    jpaRepo.save(entity);
    return Result.success(vehiculo);
  }

  @Override
  public Optional<Vehiculo> buscarPorPlaca(Placa placa) {
    return jpaRepo.findByPlaca(placa.getValue())
        .map(VehiculoJpaEntity::toDomain);
  }

  @Override
  public List<Vehiculo> listarTodos() {
    return jpaRepo.findAll().stream()
        .map(VehiculoJpaEntity::toDomain)
        .toList();
  }

  @Override
  public List<Vehiculo> listarPorCliente(String clienteId) {
    return jpaRepo.findByClienteId(clienteId).stream()
        .map(VehiculoJpaEntity::toDomain)
        .toList();
  }

  @Transactional
  @Override
  public void eliminar(Placa placa) {
    jpaRepo.deleteByPlaca(placa.getValue());
  }

}
