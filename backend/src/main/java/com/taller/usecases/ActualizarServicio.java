package com.taller.usecases;

import java.math.BigDecimal;

import com.taller.domain.entities.Servicio;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.ActualizarServicioCMD;

public class ActualizarServicio {
  private final IServicioRepository servicioRepository;

  public ActualizarServicio(IServicioRepository servicioRepository) {
    this.servicioRepository = servicioRepository;
  }

  public Result<Servicio, IErrorApp> ejecutar(ActualizarServicioCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error los datos a actualizar no pueden ser nulos"));
    }
    if (input.servicioId() == null || input.servicioId().isBlank()) {
      return Result.error(new VerificationError("Error el id ingresado es invalido"));
    }

    var servicioOPT = servicioRepository.buscarPorId(input.servicioId());

    if (servicioOPT.isEmpty()) {
      return Result.error(new VerificationError("Error no se encontro ningun servicio con el ID ingresado"));
    }

    Servicio servicio = servicioOPT.get();
    var nombre = actualizarNombre(servicio, input.nombre());

    if (!nombre.isSuccess) {
      return Result.error(nombre.getError());
    }

    var precio = actualizarPrecio(servicio, input.precio());
    if (!precio.isSuccess) {
      return Result.error(precio.getError());
    }

    servicioRepository.actualizar(servicio);
    return Result.success(servicio);
  }

  private Result<Void, IErrorApp> actualizarNombre(Servicio servicio, String nuevoNombre) {
    if (nuevoNombre == null || nuevoNombre.isBlank()) {
      return Result.success(null);
    }
    var existente = servicioRepository.buscarPorNombre(nuevoNombre);

    if (existente.isPresent()) {
      return Result.error(new VerificationError("Error ya existe un servicio con el nombre ingresado"));
    }

    servicio.cambiarNombre(nuevoNombre);
    return Result.success(null);
  }

  private Result<Void, IErrorApp> actualizarPrecio(Servicio servicio, String precioRaw) {
    if (precioRaw == null || precioRaw.isBlank()) {
      return Result.success(null);
    }

    try {
      BigDecimal precio = new BigDecimal(precioRaw);

      if (precio.compareTo(BigDecimal.ZERO) <= 0) {
        return Result.error(new VerificationError("Error el precio no puede ser menor a 0"));
      }
      servicio.cambiarPrecio(precio);
      return Result.success(null);
    } catch (NumberFormatException e) {
      return Result.error(new VerificationError("Error el dato ingresado es invalido"));
    }
  }
}
