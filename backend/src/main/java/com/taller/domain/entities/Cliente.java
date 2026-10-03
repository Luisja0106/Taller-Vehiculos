package com.taller.domain.entities;

import java.util.ArrayList;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.domain.valueobjects.Telefono;

/**
 * Representa un cliente del taller.
 *
 * un cliente es una persona que debe contener la informacion basica de
 * cualquier persona y lo mas
 * importante uno o varios vehiculo
 *
 * se crea unicamente a traves del factory method
 * {@link #crear}
 *
 * @see Persona
 * @see Vehiculo
 */

public class Cliente extends Persona {
  private final ArrayList<Vehiculo> vehiculos;

  private Cliente(String id, String nombre, Telefono telefono, Email email) {
    super(id, nombre, telefono, email);
    vehiculos = new ArrayList<Vehiculo>();
  }

  public void addVehiculo(Vehiculo vehiculo) {
    if (vehiculo == null)
      return;
    if (vehiculos.contains(vehiculo)) {
      return;
    }
    vehiculos.add(vehiculo);
    if (!this.equals(vehiculo.getDueño()))
      vehiculo.cambiarDueño(this);
  }

  void removeVehiculo(Vehiculo vehiculo) {
    if (vehiculo == null || !vehiculos.contains(vehiculo) || !vehiculo.getDueño().equals(this))
      return;
    vehiculos.remove(vehiculo);
  }

  public ArrayList<Vehiculo> getVehiculos() {
    return new ArrayList<Vehiculo>(this.vehiculos);
  }

  /**
   * Crea un empleado despues de validar que todos sus parametros sean validos en
   * el contexto del taller.
   *
   * valida que email tenga el formato correcto,
   * igual que el telefono retorna un Cliente o un error si alguna validacion
   * falla
   *
   * @param id       id unico del cliente, ej: CLI001
   * @param nombre   nombre del Cliente
   * @param telefono telefono del cliente
   * @param email    email del cliente
   *
   * @return Result con el Cliente creado si todo es valido, si no retorna un
   *         error y el motivo del mismo
   *
   * @see Result
   * @see IErrorApp
   *
   */
  public static Result<Cliente, IErrorApp> crear(String id, String nombre, String telefono, String email) {
    if (nombre == null || nombre.isBlank()) {
      return Result.error(new VerificationError("Error el nombre es invalido"));
    }
    var emailVO = Email.crear(email);
    if (!emailVO.isSuccess)
      return Result.error(emailVO.getError());

    var phoneVO = Telefono.crear(telefono);
    if (!phoneVO.isSuccess)
      return Result.error(phoneVO.getError());

    return Result.success(new Cliente(id, nombre, phoneVO.getValue(), emailVO.getValue()));
  }

}
