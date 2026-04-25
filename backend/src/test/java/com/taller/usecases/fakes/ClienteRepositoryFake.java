package com.taller.usecases.fakes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Cliente;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;

public class ClienteRepositoryFake implements IClienteRepository {

  private final List<Cliente> clientes = new ArrayList<>();
  private int contador = 0;

  @Override
  public Result<Cliente, IErrorApp> guardar(Cliente cliente) {
    clientes.add(cliente);
    return Result.success(cliente);
  }

  @Override
  public Result<Cliente, IErrorApp> actualizar(Cliente cliente) {
    clientes.removeIf(c -> c.getId().equals(cliente.getId()));
    clientes.add(cliente);
    return Result.success(cliente);
  }

  @Override
  public Optional<Cliente> buscarPorId(String id) {
    return clientes.stream()
        .filter(c -> c.getId().equals(id))
        .findFirst();
  }

  @Override
  public Optional<Cliente> buscarPorEmail(String email) {
    return clientes.stream()
        .filter(c -> c.getEmail().toString().equals(email))
        .findFirst();
  }

  @Override
  public List<Cliente> listarTodos() {
    return new ArrayList<>(clientes);
  }

  @Override
  public void eliminar(String id) {
    clientes.removeIf(c -> c.getId().equals(id));
  }

  @Override
  public int siguienteNumeroId() {
    return ++contador;
  }

}
