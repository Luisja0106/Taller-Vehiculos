package com.taller.domain.entities;

import com.taller.domain.valueobjects.Email;
import com.taller.domain.valueobjects.Telefono;

public abstract class Persona {
  protected final String id;
  private String nombre;
  private Telefono telefono;
  private Email email;

  protected Persona(String id, String nombre, Telefono telefono, Email email) {
    this.id = id;
    this.nombre = nombre;
    this.telefono = telefono;
    this.email = email;
  }

  public String getNombre() {
    return nombre;
  }

  public String getId() {
    return id;
  }

  public Telefono getTelefono() {
    return telefono;
  }

  public Email getEmail() {
    return email;
  }

  public void changeEmail(Email mail) {
    this.email = mail;
  }

  public void changePhone(Telefono number) {
    this.telefono = number;
  }

  public boolean equals(Persona persona) {
    return id.equals(persona.id);
  }

}
