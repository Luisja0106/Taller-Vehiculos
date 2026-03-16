package com.taller.core.entities;

import java.util.ArrayList;

public class Cliente extends Persona {
  private final ArrayList<Vehiculo> vehiculos;

  public Cliente(String id, String nombre, String telefono, String email) {
    super(id, nombre, telefono, email);
    vehiculos = new ArrayList<Vehiculo>();
  }

  public void addVehiculo(Vehiculo vehiculo) {
    if (vehiculo != null) {
      // TODO: add security verifications
      vehiculos.add(vehiculo);
    }
  }

  public ArrayList<Vehiculo> getVehiculos() {
    return new ArrayList<Vehiculo>(this.vehiculos);
  }

}
