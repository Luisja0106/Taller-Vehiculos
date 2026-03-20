package com.taller.domain.valueobjects;

import java.util.regex.Pattern;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

public final class Email {
  private final String valor;

  private Email(String valor) {
    this.valor = valor;
  }

  public static Result<Email, IErrorApp> crear(String valorRaw) {
    if (valorRaw == null || valorRaw.isBlank())
      return Result.error(new VerificationError("El email no puede estar vacio"));
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (!pattern.matcher(valorRaw).matches()) {
      return Result.error(new VerificationError("Error Email invalido"));
    }
    return Result.success(new Email(valorRaw.toLowerCase().trim().replaceAll("\\s", "")));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Email))
      return false;
    return this.valor.equals(((Email) obj).valor);
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
