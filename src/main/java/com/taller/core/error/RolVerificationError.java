package com.taller.core.error;

import com.taller.core.interfaces.IErrorApp;

public class RolVerificationError implements IErrorApp {

  @Override
  public String getMessage() {
    return "Error rol invalido";
  }

  @Override
  public void handle() {
    // TODO: hacer un evento para roles invalidos
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

}
