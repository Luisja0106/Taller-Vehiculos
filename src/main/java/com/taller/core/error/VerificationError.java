package com.taller.core.error;

import com.taller.core.interfaces.IErrorApp;

public class VerificationError implements IErrorApp {

  private String message;

  public VerificationError(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public void handle() {
    // TODO: create event for the error of verification
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

}
