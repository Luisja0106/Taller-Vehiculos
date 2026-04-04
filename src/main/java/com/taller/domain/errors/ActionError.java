package com.taller.domain.errors;

import com.taller.domain.interfaces.IErrorApp;

public class ActionError implements IErrorApp {

  private String message;

  public ActionError(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public void handle() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

}
