package com.taller.core.entities;

import java.util.regex.Pattern;

import com.taller.core.error.EmailVerifcationError;
import com.taller.core.error.PhoneVerificationError;
import com.taller.core.interfaces.IErrorApp;
import com.taller.core.utils.Result;

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
    if (!isValidEmail(mail).isSuccess)
      return;

    this.email = mail;
  }

  public void changePhone(String number) {
    if (!isValidPhone(number).isSuccess)
      return;

    this.telefono = number;
  }

  public boolean equals(Persona persona) {
    return id.equals(persona.id);
  }

  protected static Result<Void, IErrorApp> isValidEmail(String mail) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (!pattern.matcher(mail).matches()) {
      return Result.error(new EmailVerifcationError(mail));
    }
    return Result.success(null);
  }

  protected static Result<Void, IErrorApp> isValidPhone(String number) {
    String phoneRegex = "^[+]?\\d{7,15}$";
    Pattern pattern = Pattern.compile(phoneRegex);
    if (!pattern.matcher(number).matches()) {
      return Result.error(new PhoneVerificationError(number));
    }
    return Result.success(null);
  }

}
