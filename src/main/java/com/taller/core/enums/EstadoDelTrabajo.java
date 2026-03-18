package com.taller.core.enums;

public enum EstadoDelTrabajo {
  PENDIENTE("Pendiente"), EN_PROCESO("En Proceso"), FINALIZADO("Finalizado");

  private final String name;

  EstadoDelTrabajo(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
