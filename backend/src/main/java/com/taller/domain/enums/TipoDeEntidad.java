package com.taller.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TipoDeEntidad {
  EMP, CLI, ORD, VHC, SRV;

  public static Optional<TipoDeEntidad> buscarPorPrefijo(String codigo) {
    if (codigo == null || codigo.isBlank() || codigo.length() < 3) {
      return Optional.empty();
    }
    String prefijo = codigo.substring(0, 3).toUpperCase();
    return Arrays.stream(values())
        .filter(t -> t.name().equals(prefijo))
        .findFirst();
  }
}
