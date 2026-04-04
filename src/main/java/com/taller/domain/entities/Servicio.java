package com.taller.domain.entities;

import java.math.BigDecimal;

import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.interfaces.IServicio;
import com.taller.domain.utils.Result;

public class Servicio implements IServicio {

  private final String id;
  private final String nombre;
  private final BigDecimal precio;

  private Servicio(String id, String nombre, BigDecimal precio) {
    this.id = id;
    this.nombre = nombre;
    this.precio = precio;
  }

  public static Result<Servicio, IErrorApp> crear(String id, String nombre, BigDecimal precio) {

    if (id == null || id.isBlank()) {
      return Result.error(new VerificationError("El id no puede ser vacio"));
    }
    if (nombre == null || nombre.isBlank())
      return Result.error(new VerificationError("El nombre no puede estar vacio"));
    if (precio == null)
      return Result.error(new VerificationError("El precio no puede ser vacio"));
    if (precio.compareTo(BigDecimal.ZERO) <= 0)
      return Result.error(new VerificationError("El precio es invalido"));

    return Result.success(new Servicio(id, nombre, precio));
  }

  @Override
  public String getNombreDelServicio() {
    return nombre;
  }

  @Override
  public String getId() {
    return id;
  }

  public BigDecimal getPrecio() {
    return precio;
  }

  @Override
  public Result<BigDecimal, IErrorApp> calcularCosto() {
    // TODO: add a more complex validations
    return Result.success(this.precio);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Servicio))
      return false;
    Servicio otro = (Servicio) obj;
    return this.id.equals(otro.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

}
