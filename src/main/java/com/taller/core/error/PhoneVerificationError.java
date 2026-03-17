package com.taller.core.error;

import com.taller.core.interfaces.IErrorApp;

public class PhoneVerificationError implements IErrorApp {
  private String phone;

  public PhoneVerificationError(String phone) {
    this.phone = phone;
  }

  @Override
  public String getMessage() {
    return "El telefono " + phone + " es invalido";
  }

  @Override
  public void handle() {
    // TODO: implementar evento para error de telefono
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

}
