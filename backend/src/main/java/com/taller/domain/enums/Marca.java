package com.taller.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Marca {
  CHEVROLET("Chevrolet"), MAZDA("Mazda"), TOYOTA("Toyota"), RENAULT("Renault"), KIA("Kia");

  private final String name;

  public static Optional<Marca> buscarPorNombre(String nombre) {
    if (nombre == null || nombre.isBlank())
      return Optional.empty();
    return Arrays.stream(Marca.values())
        .filter(m -> m.name().equalsIgnoreCase(nombre.trim()) || m.name.equalsIgnoreCase(nombre.trim()))
        .findFirst();
  }

  Marca(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
