package com.taller.domain.utils;

public class Result<T, E> {
  public final boolean isSuccess;
  private T value;
  private E error;

  private Result(T value, E error, boolean success) {
    this.value = value;
    this.error = error;
    this.isSuccess = success;
  }

  public static <U, F> Result<U, F> success(U value) {
    return new Result<U, F>(value, null, true);
  }

  public static <U, F> Result<U, F> error(F error) {
    return new Result<U, F>(null, error, false);
  }

  public T getValue() {
    if (!isSuccess)
      throw new IllegalStateException("Error no se puede obtener un valor de un resultado fallido");
    return this.value;
  }

  public E getError() {
    if (isSuccess)
      throw new IllegalStateException("Error no se puede obtener un error de un resultado exitoso");
    return this.error;
  }
}
