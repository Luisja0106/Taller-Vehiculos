package com.taller.domain.entities;

import com.taller.domain.enums.Marca;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

public class Vehiculo {
  private final String placa;
  private Cliente dueño;
  private String modelo;
  private Marca marca;
  private int anio;

  private Vehiculo(String placa, Cliente dueño, String modelo, Marca marca, int anio) {
    this.placa = placa;
    this.dueño = dueño;
    this.modelo = modelo;
    this.marca = marca;
    this.anio = anio;
  }

  public static Result<Vehiculo, IErrorApp> crear(String placa, Cliente dueño, String modelo, Marca marca, int anio) {
    if (placa == null || placa.isBlank()) {
      return Result.error(new VerificationError("La placa no puede estar vacia"));
    }
    if (dueño == null) {
      return Result.error(new VerificationError("El usuario es invalido"));
    }
    if (marca == null) {
      return Result.error(new VerificationError("La marca es invalida"));
    }
    if (modelo == null || modelo.isBlank()) {
      return Result.error(new VerificationError("El modelo no puede estar vacio"));
    }
    if (anio <= 0) {
      return Result.error(new VerificationError("El año es invalido"));
    }

    String placaFormat = placa.toUpperCase().trim().replaceAll("\\s", "");

    return Result.success(new Vehiculo(placaFormat, dueño, modelo, marca, anio));
  }

  public String getPlaca() {
    return this.placa;
  }

  public Cliente getDueño() {
    return dueño;
  }

  public String getModelo() {
    return modelo;
  }

  public String getMarca() {
    return marca.toString();
  }

  public void setDueño(Cliente nuevoDueño) {
    if (nuevoDueño == null)
      return;
    if (nuevoDueño.equals(this.dueño)) {
      return;
    }

    this.dueño = nuevoDueño;
    nuevoDueño.addVehiculo(this);
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public void setMarca(Marca marca) {
    this.marca = marca;
  }

  public int getAnio() {
    return anio;
  }

  public void setAnio(int anio) {
    this.anio = anio;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Vehiculo))
      return false;
    Vehiculo otro = (Vehiculo) obj;
    return this.placa.equals(otro.placa);
  }

  @Override
  public int hashCode() {
    return this.placa.hashCode();
  }

}
