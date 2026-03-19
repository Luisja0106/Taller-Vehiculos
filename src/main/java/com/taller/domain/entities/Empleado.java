package com.taller.domain.entities;

import com.taller.domain.enums.Rol;
import com.taller.domain.enums.TipoDeContrato;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

public class Empleado extends Persona {

  private Rol rol;
  private TipoDeContrato contrato;

  private Empleado(String id, String nombre, String telefono, String email, Rol rol, TipoDeContrato contrato) {
    super(id, nombre, telefono, email);
    this.rol = rol;
    this.contrato = contrato;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  public TipoDeContrato getContrato() {
    return contrato;
  }

  public void setContrato(TipoDeContrato contrato) {
    this.contrato = contrato;
  }

  public static Result<Empleado, IErrorApp> crear(String id, String nombre, String telefono, String email, Rol rol,
      TipoDeContrato contrato) {
    var emailRes = isValidEmail(email);
    if (!emailRes.isSuccess)
      return Result.error(emailRes.getError());

    var phoneRes = isValidPhone(telefono);
    if (!phoneRes.isSuccess)
      return Result.error(phoneRes.getError());
    if (rol == null)
      return Result.error(new VerificationError("Error Rol invalido"));
    if (contrato == null)
      return Result.error(new VerificationError("Error Contrato Invalido"));

    return Result.success(new Empleado(id, nombre, telefono, email, rol, contrato));
  }
}
