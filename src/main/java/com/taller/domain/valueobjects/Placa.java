package com.taller.domain.valueobjects;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

/**
 * Representa una placa vehicular válida y normalizada como valor inmutable.
 *
 * <p>
 * Garantiza que cualquier instancia de esta clase contiene una placa
 * con formato colombiano válido (tres letras seguidas de tres números,
 * ej: "ABC123"), normalizada en mayúsculas y sin espacios. No puede
 * crearse directamente, solo a través de {@link #crear}.
 * </p>
 *
 * <p>
 * Dos instancias de Placa son iguales si contienen el mismo valor,
 * independientemente de cómo fueron creadas.
 * </p>
 *
 * @see com.taller.domain.entities.Vehiculo
 */
public final class Placa {
  private final String valor;

  private Placa(String valor) {
    this.valor = valor;
  }

  /**
   * Crea una Placa validando y normalizando el valor recibido.
   *
   * <p>
   * Normaliza la placa a mayúsculas y elimina espacios antes
   * de validar el formato colombiano.
   * </p>
   *
   * @param valorRaw placa cruda recibida del exterior, ej: "abc123", "ABC 123"
   * @return {@link Result} con la Placa válida y normalizada,
   *         o error si el valor es nulo, vacío o no cumple el formato colombiano
   */
  public static Result<Placa, IErrorApp> crear(String valorRaw) {
    if (valorRaw == null || valorRaw.isBlank())
      return Result.error(new VerificationError("La placa no puede estar vacia"));
    String placaRegex = "^[A-Z]{3}[0-9]{3}$";
    if (!valorRaw.toUpperCase().trim().matches(placaRegex))
      return Result.error(new VerificationError("Formato de placa invalido, debe ser ABC123"));
    String placaFormat = valorRaw.toUpperCase().trim().replaceAll("\\s", "");
    return Result.success(new Placa(placaFormat));
  }

  public String getValue() {
    return valor;
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
