package com.taller.domain.valueobjects;

import java.util.regex.Pattern;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

/**
 * Representa un número de teléfono válido y normalizado como valor inmutable.
 *
 * <p>
 * Garantiza que cualquier instancia de esta clase contiene un número
 * de teléfono con formato válido, entre 7 y 15 dígitos con soporte para
 * el prefijo internacional {@code +}. No puede crearse directamente,
 * solo a través de {@link #crear}.
 * </p>
 *
 * <p>
 * Dos instancias de Telefono son iguales si contienen el mismo valor,
 * independientemente de cómo fueron creadas.
 * </p>
 *
 * @see com.taller.domain.entities.Persona
 */
public final class Telefono {
  private final String valor;

  private Telefono(String valor) {
    this.valor = valor;
  }

  /**
   * Crea un Telefono validando y normalizando el valor recibido.
   *
   * <p>
   * Elimina espacios antes de validar. Acepta números locales
   * de mínimo 7 dígitos y números internacionales de hasta 15 dígitos
   * con prefijo {@code +} opcional, ej: "3001234567", "+573001234567".
   * </p>
   *
   * @param valorRaw teléfono crudo recibido del exterior
   * @return {@link Result} con el Telefono válido y normalizado,
   *         o error si el valor es nulo, vacío o tiene formato inválido
   */
  public static Result<Telefono, IErrorApp> crear(String valorRaw) {
    if (valorRaw == null || valorRaw.isBlank())
      return Result.error(new VerificationError("El Telefono no puede estar vacio"));
    String phoneRegex = "^[+]?\\d{7,15}$";
    Pattern pattern = Pattern.compile(phoneRegex);
    if (!pattern.matcher(valorRaw).matches()) {
      return Result.error(new VerificationError("Error Telefono invalido"));
    }
    return Result.success(new Telefono(valorRaw.trim()));
  }

  public String getValue() {
    return valor;
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
