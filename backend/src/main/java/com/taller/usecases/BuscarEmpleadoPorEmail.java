package com.taller.usecases;

import com.taller.domain.entities.Empleado;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.usecases.dto.BuscarPorEmailCMD;

public class BuscarEmpleadoPorEmail {

  private IEmpleadoRepository empleadoRepository;

  public BuscarEmpleadoPorEmail(IEmpleadoRepository empleadoRepository) {
    this.empleadoRepository = empleadoRepository;
  }

  public Result<Empleado, IErrorApp> ejecutar(BuscarPorEmailCMD input) {
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

    var result = empleadoRepository.buscarPorEmail(email.getValue().toString());

    if (result.isEmpty()) {
      return Result.error(new VerificationError("No se encontro el empleado con el email ingresado"));
    }
    return Result.success(result.get());
  }
}
