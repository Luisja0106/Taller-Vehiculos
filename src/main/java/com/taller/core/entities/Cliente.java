package com.taller.core.entities;

import java.util.ArrayList;

import com.taller.core.interfaces.IErrorApp;
import com.taller.core.utils.Result;

public class Cliente extends Persona {
  private final ArrayList<Vehiculo> vehiculos;

  private Cliente(String id, String nombre, String telefono, String email) {
    super(id, nombre, telefono, email);
    vehiculos = new ArrayList<Vehiculo>();
  }

  public void addVehiculo(Vehiculo vehiculo) {
    if (vehiculo == null)
      return;
    for (Vehiculo v : vehiculos) {
      if (v.equals(vehiculo))
        return;
    }
    vehiculos.add(vehiculo);
  }

  public ArrayList<Vehiculo> getVehiculos() {
    return new ArrayList<Vehiculo>(this.vehiculos);
  }

  public static Result<Cliente, IErrorApp> crear(String id, String nombre, String telefono, String email) {
    var emailRes = isValidEmail(email);
    if (!emailRes.isSuccess)
      return Result.error(emailRes.getError());

    var phoneRes = isValidPhone(telefono);
    if (!phoneRes.isSuccess)
      return Result.error(phoneRes.getError());

    return Result.success(new Cliente(id, nombre, telefono, email));
  }

}
