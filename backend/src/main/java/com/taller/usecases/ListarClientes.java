package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.Cliente;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;

public class ListarClientes {

  private final IClienteRepository clienteRepository;

  public ListarClientes(IClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  public Result<List<Cliente>, IErrorApp> ejecutar() {
    List<Cliente> list = clienteRepository.listarTodos();

    if (list == null) {
      return Result.error(new ActionError("Error al obtener la lista de clientes"));
    }
    return Result.success(list);
  }

}
