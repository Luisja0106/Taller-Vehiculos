package com.taller.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Rol {
  MECANICO, ADMINISTRADOR;

  public static Optional<Rol> buscarPorNombre(String nombre) {
    if (nombre == null || nombre.isBlank())
      return Optional.empty();
    return Arrays.stream(Rol.values())
        .filter(r -> r.name().equalsIgnoreCase(nombre.trim()))
        .findFirst();
  }
}
