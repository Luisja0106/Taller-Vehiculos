package com.taller.usecases;

import java.math.BigDecimal;

import com.taller.domain.entities.Servicio;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.CrearServicioCMD;

public class CrearServicio {
  private final IServicioRepository servicioRepository;

  public CrearServicio(IServicioRepository servicioRepository) {
    this.servicioRepository = servicioRepository;
  }

  public Result<Servicio, IErrorApp> ejecutar(CrearServicioCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error los datos no pueden ser nulos"));
    }
    if (servicioRepository.buscarPorNombre(input.nombre()).isPresent())
      return Result.error(new VerificationError("Error ya existe un Servicio con el nombre ingresado"));
    String id = setId();

    var precio = setPrecio(input.precio());

    if (!precio.isSuccess) {
      return Result.error(precio.getError());
    }

    var servicioResult = Servicio.crear(id, input.nombre(), precio.getValue());

    if (!servicioResult.isSuccess) {
      return servicioResult;
    }
    Servicio servicioNuevo = servicioResult.getValue();

    servicioRepository.guardar(servicioNuevo);
    return Result.success(servicioNuevo);
  }

  private String setId() {
    return String.format("SRV%03d", servicioRepository.siguienteNumeroParaId());
  }

  private Result<BigDecimal, IErrorApp> setPrecio(String precioRaw) {
    if (precioRaw == null || precioRaw.isBlank()) {
      return Result.error(new VerificationError("Error el precio no puede estar vacio"));
    }
    String limpio = precioRaw.trim().replace(" ", "");
    if (limpio.contains(".") && limpio.contains(",")) {
      limpio = limpio.replace(".", "").replace(",", ".");
    } else if (limpio.contains(",") && !limpio.contains(".")) {
      limpio = limpio.replace(",", ".");
    }
    try {
      var precio = new BigDecimal(limpio);

      if (precio.compareTo(BigDecimal.ZERO) <= 0) {
        return Result.error(new VerificationError("Error el precio no puede ser negatio"));
      }
      return Result.success(precio);

    } catch (NumberFormatException e) {
      return Result.error(new VerificationError("Error el precio tiene un formato invalido"));
    }
  }

}
