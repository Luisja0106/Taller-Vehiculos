package com.taller.adapters.persistence.jpa.implementations;

import java.util.List;
import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.ClienteJpaEntity;
import com.taller.adapters.persistence.jpa.repositories.ClienteJpaRepository;
import com.taller.domain.entities.Cliente;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;

import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepositoryImpl implements IClienteRepository {

  private final ClienteJpaRepository jpaRepo;

  public ClienteRepositoryImpl(ClienteJpaRepository jpaRepo) {
    this.jpaRepo = jpaRepo;
  }

  @Override
  public Result<Cliente, IErrorApp> guardar(Cliente cliente) {
    ClienteJpaEntity entity = new ClienteJpaEntity(cliente);
    jpaRepo.save(entity);
    return Result.success(cliente);
  }

  @Override
  public Result<Cliente, IErrorApp> actualizar(Cliente cliente) {
    ClienteJpaEntity entity = new ClienteJpaEntity(cliente);
    jpaRepo.save(entity);
    return Result.success(cliente);
  }

  @Override
  public Optional<Cliente> buscarPorId(String id) {
    return jpaRepo.findById(id)
        .map(ClienteJpaEntity::toDomain);
  }

  @Override
  public Optional<Cliente> buscarPorEmail(String email) {
    return jpaRepo.findByEmail(email)
        .map(ClienteJpaEntity::toDomain);
  }

  @Override
  public List<Cliente> listarTodos() {
    return jpaRepo.findAll().stream()
        .map(ClienteJpaEntity::toDomain)
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
