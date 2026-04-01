package com.taller.usecases;

import com.taller.domain.entities.Empleado;
import com.taller.domain.enums.Rol;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.domain.valueobjects.Telefono;
import com.taller.usecases.dto.ActualizarEmpleadoCMD;

public class ActualizarEmpleado {
  private final IEmpleadoRepository empleadoRepository;

  public ActualizarEmpleado(IEmpleadoRepository empleadoRepository) {
    this.empleadoRepository = empleadoRepository;
  }

  public Result<Empleado, IErrorApp> ejecutar(ActualizarEmpleadoCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error los datos no pueden ser nulos"));
    }
    var empleadoOPT = empleadoRepository.buscarPorId(input.idDelEmpleado());
    if (empleadoOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro el empleado a modificar"));
    }
    Empleado empleado = empleadoOPT.get();
    var email = actualizarEmail(empleado, input.email());
    if (!email.isSuccess) {
      return Result.error(email.getError());
    }
    var telefono = actualizarTelefono(empleado, input.telefono());
    if (!telefono.isSuccess) {
      return Result.error(telefono.getError());
    }
    var rol = actualizarRol(empleado, input.rol());
    if (!rol.isSuccess) {
      return Result.error(rol.getError());
    }
    empleadoRepository.actualizar(empleado);
    return Result.success(empleado);
  }

  private Result<Void, IErrorApp> actualizarEmail(Empleado empleado, String emailRaw) {
    if (emailRaw == null || emailRaw.isEmpty())
      return Result.success(null);
    var email = Email.crear(emailRaw);
    if (!email.isSuccess) {
      return Result.error(email.getError());
    }
    empleado.changeEmail(email.getValue());
    return Result.success(null);
  }

  private Result<Void, IErrorApp> actualizarTelefono(Empleado empleado, String telefonoRaw) {
    if (telefonoRaw == null || telefonoRaw.isEmpty())
      return Result.success(null);
    var telefono = Telefono.crear(telefonoRaw);
    if (!telefono.isSuccess) {
      return Result.error(telefono.getError());
    }
    empleado.changePhone(telefono.getValue());
    return Result.success(null);
  }

  private Result<Void, IErrorApp> actualizarRol(Empleado empleado, String rolRaw) {
    if (rolRaw == null || rolRaw.isEmpty())
      return Result.success(null);
    var rol = Rol.buscarPorNombre(rolRaw);
    if (rol.isEmpty()) {
      return Result.error(new VerificationError("Error el rol no es valido"));
    }
    empleado.setRol(rol.get());
    return Result.success(null);
  }
}
