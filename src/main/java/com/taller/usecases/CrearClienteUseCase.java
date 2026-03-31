package com.taller.usecases;

import com.taller.domain.entities.Cliente;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.usecases.dto.CrearClienteInput;

public class CrearClienteUseCase {

  private final IClienteRepository clienteRepository;

  public CrearClienteUseCase(IClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  public Result<Cliente, IErrorApp> execute(CrearClienteInput input) {
    if (clienteRepository.buscarPorId(input.id()).isPresent()) {
      return Result.error(new ActionError("El cliente ya esta registrado"));
    }
    var email = Email.crear(input.email());
    if (!email.isSuccess) {
      return Result.error(email.getError());
    }
    if (clienteRepository.buscarPorEmail(email.getValue()).isPresent()) {
      return Result.error(new ActionError("El email ya esta registrado"));
    }
    var clientResult = Cliente.crear(input.id(), input.nombre(), input.telefono(),
        input.email());

    if (!clientResult.isSuccess) {
      return clientResult;
    }
    Cliente nuevoCliente = clientResult.getValue();

    clienteRepository.guardar(nuevoCliente);
    return Result.success(nuevoCliente);
  }
}
