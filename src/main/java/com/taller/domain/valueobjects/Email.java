package com.taller.domain.valueobjects;

import java.util.regex.Pattern;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

/**
 * Representa un email válido y normalizado como valor inmutable.
 *
 * <p>
 * Garantiza que cualquier instancia de esta clase contiene un email
 * con formato correcto, en minúsculas y sin espacios. No puede crearse
 * directamente, solo a través de {@link #crear}.
 * </p>
 *
 * <p>
 * Dos instancias de Email son iguales si contienen el mismo valor,
 * independientemente de cómo fueron creadas.
 * </p>
 *
 * @see com.taller.domain.entities.Persona
 */
public final class Email {
  private final String valor;

  private Email(String valor) {
    this.valor = valor;
  }

  /**
   * Crea un Email validando y normalizando el valor recibido.
   *
   * <p>
   * Normaliza el email a minúsculas y elimina espacios antes
   * de crear la instancia.
   * </p>
   *
   * @param valorRaw email crudo recibido del exterior
   * @return {@link Result} con el Email válido y normalizado,
   *         o error si el valor es nulo, vacío o tiene formato inválido
   */
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
