package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Cliente;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;

public interface EmpleadoRepository {
  Result<Cliente, IErrorApp> guardar(Cliente cliente);

  Result<Cliente, IErrorApp> actualizar(Cliente cliente);

  Optional<Cliente> buscarPorId(String id);

  List<Cliente> listarTodos();

  Optional<Cliente> buscarPorEmail(Email email);

  void eliminar(String id);

  int siguienteNumeroId();

}
