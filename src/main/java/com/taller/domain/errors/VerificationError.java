package com.taller.domain.errors;

import com.taller.domain.interfaces.IErrorApp;

/**
 * Representa un error de verificación de datos en el dominio.
 *
 * <p>
 * Se usa cuando un dato recibido no cumple las reglas de negocio,
 * como un email con formato inválido, una placa vacía o un rol nulo.
 * Es el error más común del dominio y siempre incluye un mensaje
 * descriptivo de qué falló.
 * </p>
 *
 * @see IErrorApp
 */
public class VerificationError implements IErrorApp {

  private String message;

  /**
   * Crea un error de verificación con un mensaje descriptivo.
   *
   * @param message descripción del dato inválido, ej: "Email inválido"
   */
  public VerificationError(String message) {
    this.message = message;
  }

  /**
   * Retorna el mensaje descriptivo del error.
   *
   * @return mensaje que describe qué dato falló la verificación
   */
  @Override
  public String getMessage() {
    return message;
  }

  /**
   * Maneja el error disparando un evento de verificación.
   *
   * @throws UnsupportedOperationException hasta que el Event Pattern
   *                                       esté implementado
   */
  @Override
  public void handle() {
    // TODO: create event for the error of verification
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

}
