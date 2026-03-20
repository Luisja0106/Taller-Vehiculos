package com.taller.domain.valueobjects;

import java.util.regex.Pattern;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

public final class Telefono {
  private final String valor;

  private Telefono(String valor) {
    this.valor = valor;
  }

  public static Result<Telefono, IErrorApp> crear(String valorRaw) {
    if (valorRaw == null || valorRaw.isBlank())
      return Result.error(new VerificationError("El Telefono no puede estar vacio"));
    String phoneRegex = "^[+]?\\d{7,15}$";
    Pattern pattern = Pattern.compile(phoneRegex);
    if (!pattern.matcher(valorRaw).matches()) {
      return Result.error(new VerificationError("Error Telefono invalido"));
    }
    return Result.success(new Telefono(valorRaw.trim().replaceAll("\\s", "")));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Telefono))
      return false;
    return this.valor.equals(((Telefono) obj).valor);
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
