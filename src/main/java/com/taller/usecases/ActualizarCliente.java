package com.taller.usecases;

import com.taller.domain.entities.Cliente;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.domain.valueobjects.Telefono;
import com.taller.usecases.dto.ActualizarClienteCMD;

public class ActualizarCliente {

  private final IClienteRepository clienteRepository;

  public ActualizarCliente(IClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  public Result<Cliente, IErrorApp> ejecutar(ActualizarClienteCMD input) {
    if (input == null)
      return Result.error(new ActionError("Error los datos a actualizar no pueden ser nulos"));

    var clienteOPT = clienteRepository.buscarPorId(input.idDelCliente());

    if (clienteOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro el cliente con el id registrado"));
    }
    Cliente cliente = clienteOPT.get();

    var email = actualizarEmail(cliente, input.email());
    if (!email.isSuccess) {
      return Result.error(email.getError());
    }
    var telefono = actualizarTelefono(cliente, input.telefono());
    if (!telefono.isSuccess) {
      return Result.error(telefono.getError());
    }

    var nombre = actualizarNombre(cliente, input.nombre());
    if (!nombre.isSuccess) {
      return Result.error(nombre.getError());
    }

    clienteRepository.actualizar(cliente);
    return Result.success(cliente);
  }

  private Result<Void, IErrorApp> actualizarEmail(Cliente cliente, String emailRaw) {
    if (emailRaw == null || emailRaw.isEmpty())
      return Result.success(null);

    var email = Email.crear(emailRaw);

    if (!email.isSuccess) {
      return Result.error(email.getError());
    }

    if (clienteRepository.buscarPorEmail(email.getValue().toString()).isPresent()) {
      return Result.error(new VerificationError("Error el Email ingresado ya se encuentra registrado"));
    }
    cliente.changeEmail(email.getValue());
    return Result.success(null);
  }

  private Result<Void, IErrorApp> actualizarTelefono(Cliente cliente, String telefonoRaw) {
    if (telefonoRaw == null || telefonoRaw.isEmpty())
      return Result.success(null);

    var telefono = Telefono.crear(telefonoRaw);

    if (!telefono.isSuccess) {
      return Result.error(telefono.getError());
    }
    cliente.changePhone(telefono.getValue());
    return Result.success(null);
  }

  private Result<Void, IErrorApp> actualizarNombre(Cliente cliente, String nombre) {
    if (nombre == null || nombre.isBlank()) {
      return Result.success(null);
    }

    cliente.changeNombre(nombre);

    return Result.success(null);
  }

}
