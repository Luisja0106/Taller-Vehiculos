package com.taller.core.entities;

import com.taller.core.enums.Marca;

public class Vehiculo {
  private final String placa;
  private Cliente dueño;
  private String modelo;
  private Marca marca;
  private int anio;

  public Vehiculo(String placa, Cliente dueño, String modelo, Marca marca, int anio) {
    this.placa = placa;
    this.dueño = dueño;
    this.modelo = modelo;
    this.marca = marca;
    this.anio = anio;
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
    if (!nuevoDueño.equals(this.dueño)) {
      // TODO: logica de remover dueño
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

}
