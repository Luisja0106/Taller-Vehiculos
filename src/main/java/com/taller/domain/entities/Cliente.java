package com.taller.domain.entities;

import java.util.ArrayList;

import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.domain.valueobjects.Telefono;

public class Cliente extends Persona {
  private final ArrayList<Vehiculo> vehiculos;

  private Cliente(String id, String nombre, Telefono telefono, Email email) {
    super(id, nombre, telefono, email);
    vehiculos = new ArrayList<Vehiculo>();
  }

  public void addVehiculo(Vehiculo vehiculo) {
    if (vehiculo == null)
      return;
    if (!vehiculos.contains(vehiculo)) {
      return;
    }
    vehiculos.add(vehiculo);
    if (!this.equals(vehiculo.getDueño()))
      vehiculo.setDueño(this);
  }

  public ArrayList<Vehiculo> getVehiculos() {
    return new ArrayList<Vehiculo>(this.vehiculos);
  }

  public static Result<Cliente, IErrorApp> crear(String id, String nombre, String telefono, String email) {
    var emailVO = Email.crear(email);
    if (!emailVO.isSuccess)
      return Result.error(emailVO.getError());

    var phoneVO = Telefono.crear(telefono);
    if (!phoneVO.isSuccess)
      return Result.error(phoneVO.getError());

    return Result.success(new Cliente(id, nombre, phoneVO.getValue(), emailVO.getValue()));
  }

}
