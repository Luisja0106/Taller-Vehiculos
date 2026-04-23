package com.taller.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TipoDeContrato {
  FIJO, PARCIAL;

  public static Optional<TipoDeContrato> buscarPorNombre(String nombre) {
    if (nombre == null || nombre.isBlank())
      return Optional.empty();
    return Arrays.stream(TipoDeContrato.values())
        .filter(t -> t.name().equalsIgnoreCase(nombre.trim()))
        .findFirst();
  }
}
