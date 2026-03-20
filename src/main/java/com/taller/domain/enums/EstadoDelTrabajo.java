package com.taller.domain.enums;

public enum EstadoDelTrabajo {
  PENDIENTE("Pendiente"), EN_PROCESO("En Proceso"), EN_ESPERA_DE_PAGO("En espera de pago"), FINALIZADO("Finalizado");

  private final String name;

  EstadoDelTrabajo(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
