package com.taller.usecases;

import com.taller.domain.entities.Cliente;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.CrearClienteCMD;

public class RegistrarCliente {

  private final IClienteRepository clienteRepository;

  public RegistrarCliente(IClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  public Result<Cliente, IErrorApp> ejecutar(CrearClienteCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Los datos no pueden ser nulos"));
    }
    String id = setId();
    if (clienteRepository.buscarPorEmail(input.email()).isPresent()) {
      return Result.error(new ActionError("El email ya esta registrado"));
    }
    var clientResult = Cliente.crear(id, input.nombre(), input.telefono(),
        input.email());

    if (!clientResult.isSuccess) {
      return clientResult;
    }
    Cliente nuevoCliente = clientResult.getValue();

    clienteRepository.guardar(nuevoCliente);
    return Result.success(nuevoCliente);
  }

  private String setId() {
    return String.format("CLI%03d", clienteRepository.siguienteNumeroId());
  }
}
