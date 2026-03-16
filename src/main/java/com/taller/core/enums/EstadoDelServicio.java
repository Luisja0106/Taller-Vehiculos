package com.taller.core.enums;

public enum EstadoDelServicio {
  PENDIENTE("Pendiente"), EN_PROCESO("En Proceso"), FINALIZADO("Finalizado");

  private final String name;

  EstadoDelServicio(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
