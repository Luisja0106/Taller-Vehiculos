package com.taller.core.entities;

import java.util.regex.Pattern;

public abstract class Persona {
  protected final String id;
  private String nombre;
  private String telefono;
  private String email;

  protected Persona(String id, String nombre, String telefono, String email) {
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

  public String getTelefono() {
    return telefono;
  }

  public String getEmail() {
    return email;
  }

  public void changeEmail(String mail) {
    if (!isValidEmail(mail))
      return;

    this.email = mail;
  }

  public void changePhone(String number) {
    if (!isValidPhone(number))
      return;

    this.telefono = number;
  }

  public boolean equals(Persona persona) {
    return id.equals(persona.id);
  }

  private boolean isValidEmail(String mail) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(mail).matches();
  }

  private boolean isValidPhone(String number) {
    String phoneRegex = "^[+]?\\d{7,15}$";
    Pattern pattern = Pattern.compile(phoneRegex);
    return pattern.matcher(number).matches();
  }

}
