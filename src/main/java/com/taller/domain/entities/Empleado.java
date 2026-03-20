package com.taller.domain.entities;

import com.taller.domain.enums.Rol;
import com.taller.domain.enums.TipoDeContrato;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.domain.valueobjects.Telefono;

public class Empleado extends Persona {

  private Rol rol;
  private TipoDeContrato contrato;

  private Empleado(String id, String nombre, Telefono telefono, Email email, Rol rol, TipoDeContrato contrato) {
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
    var emailVO = Email.crear(email);
    if (!emailVO.isSuccess)
      return Result.error(emailVO.getError());

    var phoneVO = Telefono.crear(telefono);
    if (!phoneVO.isSuccess)
      return Result.error(phoneVO.getError());
    if (rol == null)
      return Result.error(new VerificationError("Error Rol invalido"));
    if (contrato == null)
      return Result.error(new VerificationError("Error Contrato Invalido"));

    return Result.success(new Empleado(id, nombre, phoneVO.getValue(), emailVO.getValue(), rol, contrato));
  }
}
