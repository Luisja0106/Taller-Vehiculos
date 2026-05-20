package com.taller.domain.utils;

/**
 * Representa el resultado de una operación que puede tener éxito o fallar.
 *
 * <p>
 * Es la alternativa al uso de excepciones para errores esperados dentro
 * del dominio. Fuerza a quien llama a manejar explícitamente tanto el caso
 * de éxito como el de error, haciendo el flujo de errores visible y
 * predecible en vez de sorpresivo.
 * </p>
 *
 * <p>
 * Uso típico:
 * </p>
 * 
 * <pre>
 *   Result&lt;Empleado, IErrorApp&gt; resultado = Empleado.crear(...);
 *
 *   if (!resultado.isSuccess) {
 *       System.out.println(resultado.getError().getMessage());
 *       return;
 *   }
 *
 *   Empleado empleado = resultado.getValue();
 * </pre>
 *
 * @param <T> tipo del valor en caso de éxito
 * @param <E> tipo del error en caso de fallo
 *
 * @see com.taller.domain.interfaces.IErrorApp
 */
public class Result<T, E> {
  /**
   * Indica si la operación fue exitosa.
   * Verificar este campo antes de llamar a {@link #getValue()} o
   * {@link #getError()}.
   */
  public final boolean isSuccess;
  private T value;
  private E error;

  private Result(T value, E error, boolean success) {
    this.value = value;
    this.error = error;
    this.isSuccess = success;
  }

  /**
   * Crea un resultado exitoso con el valor producido.
   *
   * @param <U>   tipo del valor
   * @param <F>   tipo del error
   * @param value valor resultante de la operación exitosa
   * @return Result en estado de éxito con el valor dado
   */
  public static <U, F> Result<U, F> success(U value) {
    return new Result<U, F>(value, null, true);
  }

  /**
   * Crea un resultado fallido con el error ocurrido.
   *
   * @param <U>   tipo del valor
   * @param <F>   tipo del error
   * @param error error que describe qué salió mal
   * @return Result en estado de error con el error dado
   */
  public static <U, F> Result<U, F> error(F error) {
    return new Result<U, F>(null, error, false);
  }

  /**
   * Retorna el valor del resultado exitoso.
   *
   * <p>
   * Siempre verificar {@link #isSuccess} antes de llamar este método.
   * Llamarlo en un resultado fallido lanza una excepción.
   * </p>
   *
   * @return valor de la operación exitosa
   * @throws IllegalStateException si el resultado es un error
   */
  public T getValue() {
    if (!isSuccess)
      throw new IllegalStateException("Error no se puede obtener un valor de un resultado fallido");
    return this.value;
  }

  /**
   * Retorna el error del resultado fallido.
   *
   * <p>
   * Siempre verificar {@link #isSuccess} antes de llamar este método.
   * Llamarlo en un resultado exitoso lanza una excepción.
   * </p>
   *
   * @return error que describe qué salió mal
   * @throws IllegalStateException si el resultado es exitoso
   */
  public E getError() {
    if (isSuccess)
      throw new IllegalStateException("Error no se puede obtener un error de un resultado exitoso");
    return this.error;
  }
}
