package com.taller.domain.enums;

public enum Marca {
  CHEVROLET("Chevrolet"), MAZDA("Mazda"), TOYOTA("Toyota"), RENAULT("Renault"), KIA("Kia");

  private final String name;

  Marca(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
