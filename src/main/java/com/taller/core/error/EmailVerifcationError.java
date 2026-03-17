package com.taller.core.error;

import com.taller.core.interfaces.IErrorApp;

public class EmailVerifcationError implements IErrorApp {

  private String mail;

  public EmailVerifcationError(String mail) {
    this.mail = mail;
  }

  @Override
  public String getMessage() {
    return "El formatto de " + mail + "No es valido";
  }

  @Override
  public void handle() {
    // TODO: create event for error creation email
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

}
