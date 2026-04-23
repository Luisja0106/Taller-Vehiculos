package com.taller.usecases;

import java.math.BigDecimal;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.errors.ActionError;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.RegistrarPagoCMD;

public class RegistrarPago {

  private final IOrdenRepository ordenRepository;

  public RegistrarPago(IOrdenRepository ordenRepository) {
    this.ordenRepository = ordenRepository;
  }

  public Result<OrdenDeTrabajo, IErrorApp> ejecutar(RegistrarPagoCMD input) {
    if (input == null) {
      return Result.error(new ActionError("Error los datos no pueden ser nulos"));
    }
    var orden = ordenRepository.buscarPorId(input.ordenId());

    if (orden.isEmpty()) {
      return Result.error(new VerificationError("Error no se pudo encontrar la orden"));
    }
    var precio = setPrecio(input.pago());

    if (!precio.isSuccess) {
      return Result.error(precio.getError());
    }

    var resultado = orden.get().registrarPago(precio.getValue());

    if (!resultado.isSuccess) {
      return Result.error(resultado.getError());
    }

    OrdenDeTrabajo ordenNueva = orden.get();

    ordenRepository.actualizar(ordenNueva);

    return Result.success(ordenNueva);
  }

  private Result<BigDecimal, IErrorApp> setPrecio(String valorRaw) {
    if (valorRaw == null || valorRaw.isBlank()) {
      return Result.error(new VerificationError("Error el precio no puede estar vacio"));
    }
    String format = valorRaw.trim().replace(" ", "");
    if (format.contains(".") && format.contains(",")) {
      format = format.replace(".", "").replace(",", ".");
    } else if (format.contains(",") && !format.contains(".")) {
      format = format.replace(",", ".");
    }
    BigDecimal precio;
    try {
      precio = new BigDecimal(format);
      if (precio.compareTo(BigDecimal.ZERO) < 0) {
        return Result.error(new VerificationError("Error el valor no puede ser menor o igual que 0"));
      }
      return Result.success(precio);
    } catch (NumberFormatException e) {
      return Result.error(new VerificationError("Error el formato del precio es incorrecto"));
    }
  }
}
