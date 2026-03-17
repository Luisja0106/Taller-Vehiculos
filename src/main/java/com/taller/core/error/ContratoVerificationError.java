package com.taller.core.error;

import com.taller.core.interfaces.IErrorApp;

public class ContratoVerificationError implements IErrorApp {

  @Override
  public String getMessage() {
    return "Error cotrato invalido";
  }

  @Override
  public void handle() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

}
