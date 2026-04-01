package com.taller.usecases;

import com.taller.domain.entities.Empleado;
import com.taller.domain.enums.Rol;
import com.taller.domain.enums.TipoDeContrato;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.CrearEmpleadoCMD;

public class ContratarEmpleado {

  private final IEmpleadoRepository empleadoRepository;

  public ContratarEmpleado(IEmpleadoRepository empleadoRepository) {
    this.empleadoRepository = empleadoRepository;
  }

  public Result<Empleado, IErrorApp> ejecutar(CrearEmpleadoCMD input) {
    if (input == null)
      return Result.error(new ActionError("Los datos no pueden ser nulos"));

    String id = setId();
    var email = empleadoRepository.buscarPorEmail(input.email());
    if (email.isPresent()) {
      return Result.error(new VerificationError("Error email ya registrado"));
    }
    var rol = Rol.buscarPorNombre(input.rol());
    if (rol.isEmpty()) {
      return Result.error(new VerificationError("Rol invalido"));
    }
    var tipoDeContrato = TipoDeContrato.buscarPorNombre(input.contrato());
    if (tipoDeContrato.isEmpty()) {
      return Result.error(new VerificationError("Contrato invalido"));
    }
    var emplaedoResult = Empleado.crear(id, input.nombre(), input.telefono(), input.email(), rol.get(),
        tipoDeContrato.get());
    if (!emplaedoResult.isSuccess) {
      return emplaedoResult;
    }
    Empleado empleadoNuevo = emplaedoResult.getValue();

    empleadoRepository.guardar(empleadoNuevo);
    return Result.success(empleadoNuevo);
  }

  private String setId() {
    return String.format("EMP%03d", empleadoRepository.siguienteNumeroId());
  }
}
