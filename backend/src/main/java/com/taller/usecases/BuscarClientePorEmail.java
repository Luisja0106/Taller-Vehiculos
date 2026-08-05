package com.taller.usecases;

import com.taller.domain.entities.Cliente;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.usecases.dto.BuscarPorEmailCMD;

public class BuscarClientePorEmail {

  private IClienteRepository clienteRepository;

  public BuscarClientePorEmail(IClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  public Result<Cliente, IErrorApp> ejecutar(BuscarPorEmailCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error el input no puede ser nulo"));
    }
    if (input.email() == null || input.email().isBlank()) {
      return Result.error(new ActionError("Error no se puede buscar con el email vacio"));
    }

    var email = Email.crear(input.email());

    if (!email.isSuccess) {
      return Result.error(email.getError());
    }

    var result = clienteRepository.buscarPorEmail(email.getValue().toString());

    if (result.isEmpty()) {
      return Result.error(new VerificationError("No se encontro el cliente con el email ingresado"));
    }
    return Result.success(result.get());
  }
}
