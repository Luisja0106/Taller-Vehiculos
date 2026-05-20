package com.taller.domain.entities;

import com.taller.domain.valueobjects.Email;
import com.taller.domain.valueobjects.Telefono;

/**
 * clase abstracta que define los parametros basicos que toda persona debe
 * tener.
 * 
 * @param id       identificador unico de la persona (unico segun el hijo)
 * @param nombre   nombre de la persona
 * @param telefono telefono del persona
 * @param email    email de la persona
 */
public abstract class Persona {
  protected final String id;
  private String nombre;
  private Telefono telefono;
  private Email email;

  /**
   * constructor protegido debido a que luego se crean los objetos por medio de
   * una factory
   */
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

  public void changeNombre(String nombre) {
    if (nombre == null || nombre.isBlank()) {
      return;
    }
    this.nombre = nombre;
  }

  public boolean equals(Persona persona) {
    return id.equals(persona.id);
  }

}
