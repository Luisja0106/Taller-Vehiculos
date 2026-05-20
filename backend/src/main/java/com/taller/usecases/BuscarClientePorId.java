package com.taller.usecases;

import com.taller.domain.entities.Cliente;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.BuscarClientePorIdCMD;

public class BuscarClientePorId {

  private final IClienteRepository clienteRepo;

  public BuscarClientePorId(IClienteRepository clienteRepo) {
    this.clienteRepo = clienteRepo;
  }

  public Result<Cliente, IErrorApp> ejecutar(BuscarClientePorIdCMD input) {

    if (input == null) {
      return Result.error(new ActionError("Error el input no puede ser nulo"));
    }

    if (input.clienteId() == null || input.clienteId().isBlank()) {
      return Result.error(new ActionError("Error no se puede buscar con el id vacio"));
    }

    var result = clienteRepo.buscarPorId(input.clienteId());

    if (result.isEmpty()) {
      return Result.error(new ActionError("No se encontro el cliente con el id ingresado"));
    }

    return Result.success(result.get());
  }

}
