package com.taller.usecases;

import com.taller.domain.entities.Empleado;
import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.ReasignarEmpleadoAOrdenCMD;

public class ReasignarEmpleadoAOrden {

  private final IEmpleadoRepository empleadoRepository;
  private final IOrdenRepository ordenRepository;

  public ReasignarEmpleadoAOrden(IEmpleadoRepository empleadoRepository, IOrdenRepository ordenRepository) {
    this.empleadoRepository = empleadoRepository;
    this.ordenRepository = ordenRepository;
  }

  public Result<OrdenDeTrabajo, IErrorApp> ejecutar(ReasignarEmpleadoAOrdenCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error no los datos no pueden ser nulos"));
    }
    var ordenOPT = ordenRepository.buscarPorId(input.orderId());
    if (ordenOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se ha encontrado la orden del id ingresado"));
    }
    var empleadoOPT = empleadoRepository.buscarPorId(input.nuevoEmpleadoId());
    if (empleadoOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se ha encontrado el id del empleado nuevo"));
    }
    OrdenDeTrabajo orden = ordenOPT.get();
    Empleado empleado = empleadoOPT.get();

    var resultado = orden.cambiarEmpleadoACargo(empleado);

    if (!resultado.isSuccess) {
      return Result.error(resultado.getError());
    }

    ordenRepository.actualizar(orden);
    return Result.success(orden);

  }
}
