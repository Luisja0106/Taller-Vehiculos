package com.taller.domain.interfaces;

/**
 * Contrato base para todos los errores de la aplicación.
 *
 * <p>
 * Define el comportamiento mínimo que debe tener cualquier error
 * del dominio. Permite tratar todos los errores de forma uniforme
 * a través de {@link com.taller.domain.utils.Result}, sin importar
 * su tipo específico.
 * </p>
 *
 * <p>
 * Cada implementación representa una categoría distinta de error:
 * </p>
 * <ul>
 * <li>{@link com.taller.domain.errors.VerificationError} para datos
 * inválidos</li>
 * </ul>
 *
 * @see com.taller.domain.utils.Result
 * @see com.taller.domain.errors.VerificationError
 */
public interface IErrorApp {
  /**
   * Retorna el mensaje descriptivo del error.
   *
   * @return mensaje legible que describe qué salió mal
   */
  String getMessage();

  /**
   * Ejecuta la lógica de manejo del error.
   *
   * <p>
   * Cada implementación decide cómo reaccionar, ya sea
   * disparando un evento, logueando, o notificando al usuario.
   * Este método será el punto de entrada del Event Pattern
   * cuando esté implementado.
   * </p>
   */
  void handle();
}
