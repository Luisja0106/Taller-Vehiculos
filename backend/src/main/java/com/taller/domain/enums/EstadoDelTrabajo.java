package com.taller.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum EstadoDelTrabajo {
  PENDIENTE("Pendiente"), EN_PROCESO("En Proceso"), EN_ESPERA_DE_PAGO("En espera de pago"), FINALIZADO("Finalizado");

  private final String name;

  public static Optional<EstadoDelTrabajo> buscarPorNombre(String nombre) {
    if (nombre == null || nombre.isBlank()) {
      return Optional.empty();
    }
    return Arrays.stream(EstadoDelTrabajo.values())
        .filter(e -> e.name().equalsIgnoreCase(nombre.trim()) || e.name.equalsIgnoreCase(nombre.trim()))
        .findFirst();
  }

  EstadoDelTrabajo(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
