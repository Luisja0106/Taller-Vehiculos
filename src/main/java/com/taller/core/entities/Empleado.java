package com.taller.core.entities;

import com.taller.core.enums.Rol;
import com.taller.core.enums.TipoDeContrato;

public class Empleado extends Persona {

  private Rol rol;
  private TipoDeContrato contrato;

  public Empleado(String id, String nombre, String telefono, String email, Rol rol, TipoDeContrato contrato) {
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

}
