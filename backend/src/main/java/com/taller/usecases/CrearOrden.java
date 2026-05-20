package com.taller.usecases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;
import com.taller.usecases.dto.CrearOrdenCMD;

public class CrearOrden {
  private final IOrdenRepository ordenRepository;
  private final IVehiculoRepository vehiculoRepository;
  private final IEmpleadoRepository empleadoRepository;

  public CrearOrden(IOrdenRepository ordenRepository, IVehiculoRepository vehiculoRepository,
      IEmpleadoRepository empleadoRepository) {
    this.ordenRepository = ordenRepository;
    this.vehiculoRepository = vehiculoRepository;
    this.empleadoRepository = empleadoRepository;
  }

  public Result<OrdenDeTrabajo, IErrorApp> ejecutar(CrearOrdenCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error los datos no pueden ser nulos"));
    }
    String id = setId();
    var placa = Placa.crear(input.id_vehiculo());
    if (!placa.isSuccess)
      return Result.error(placa.getError());
    var vehiculo = vehiculoRepository.buscarPorPlaca(placa.getValue());
    if (vehiculo.isEmpty()) {
      return Result.error(new VerificationError("Error vehiculo no encontrado"));
    }
    if (input.id_mecanico() == null) {
      return Result.error(new VerificationError("Error el ID del Empleado a cargo no puede ser nulo"));
    }
    var empleadoACargo = empleadoRepository.buscarPorId(input.id_mecanico().trim());
    if (empleadoACargo.isEmpty())
      return Result.error(new VerificationError("Error Empleado a cargo no encontrado"));

    var ordenResult = OrdenDeTrabajo.crear(id, vehiculo.get(), empleadoACargo.get());
    if (!ordenResult.isSuccess) {
      return ordenResult;
    }
    OrdenDeTrabajo ordenNueva = ordenResult.getValue();

    ordenRepository.guardar(ordenNueva);
    return Result.success(ordenNueva);
  }

  private String setId() {
    return String.format("ORD%s%03d", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
        ordenRepository.siguienteNumeroParaId());
  }
}
