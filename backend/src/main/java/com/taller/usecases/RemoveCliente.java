package com.taller.usecases;

import com.taller.domain.entities.Cliente;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.utils.Result;

public class RemoveCliente {

  private final IClienteRepository clienteRepo;

  public RemoveCliente(IClienteRepository clienteRepo) {
    this.clienteRepo = clienteRepo;
  }

  public Result<Void, IErrorApp> ejecutar(String clienteId) {
    if (clienteId == null || clienteId.isEmpty()) {
      return Result.error(new VerificationError("El id no puede ser invalido"));
    }
    var resultado = clienteRepo.buscarPorId(clienteId);

    if (resultado.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro el id ingresado"));
    }
    Cliente cliente = resultado.get();

    if (!cliente.getVehiculos().isEmpty()) {
      return Result.error(new VerificationError(
          "Error el cliente cuenta con vehiculos, cambie el dueño de los vehiculos o eliminelos y vuelva a intentar"));
    }

    clienteRepo.eliminar(clienteId);

    return Result.success(null);
  }
}
