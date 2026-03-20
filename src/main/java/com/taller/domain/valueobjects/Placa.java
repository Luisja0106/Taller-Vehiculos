package com.taller.domain.valueobjects;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

public final class Placa {
  private final String valor;

  private Placa(String valor) {
    this.valor = valor;
  }

  public static Result<Placa, IErrorApp> crear(String valorRaw) {
    if (valorRaw == null || valorRaw.isBlank())
      return Result.error(new VerificationError("La placa no puede estar vacia"));
    String placaFormat = valorRaw.toUpperCase().trim().replaceAll("\\s", "");
    return Result.success(new Placa(placaFormat));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Placa))
      return false;
    return this.valor.equals(((Placa) obj).valor);
  }

  @Override
  public int hashCode() {
    return valor.hashCode();
  }

  @Override
  public String toString() {
    return valor;
  }

}
