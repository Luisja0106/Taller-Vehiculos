package com.taller.core.entities;

public abstract class Persona {
  protected String id;
  private String nombre;
  private String telefono;
  private String email;

  public Persona(String id, String nombre, String telefono, String email) {
    this.id = id;
    this.nombre = nombre;
    this.telefono = telefono;
    this.email = verifyEmail(email);
  }

  public String getNombre() {
    return nombre;
  }

  public String getId() {
    return id;
  }

  public String getTelefono() {
    return telefono;
  }

  public String getEmail() {
    return email;
  }

  private String verifyEmail(String mail) {
    // TODO: add a regex for verify the Email
    return mail;
  }

  public boolean equals(Persona persona) {
    return id.equals(persona.id);
  }
}
