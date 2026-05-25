package com.taller.domain.entities;

import com.taller.domain.enums.Rol;
import com.taller.domain.enums.TipoDeContrato;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;
import com.taller.domain.valueobjects.Telefono;

/**
 * Un empleado es un trabajador de la empresa.
 *
 * Un empleado es una persona que tiene un rol especifico y un tipo de contrato.
 * No puede existir un empleado con un rol o tipo de contrato invalido.
 *
 * se crea a traves de la factory {@link #crear}
 */

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

  /**
   * Crea un empleado validando todos sus datos antes de construirlo.
   *
   * Valida que el email tenga formato correcto, que el teléfono sea válido,
   * y que el rol y contrato no sean nulos. Si alguna validación falla,
   * retorna un error descriptivo sin crear el objeto.
   *
   * @param id       identificador único del empleado, ej: "EMP001"
   * @param nombre   nombre completo del empleado
   * @param telefono teléfono en formato colombiano, ej: "3001234567"
   * @param email    correo electrónico válido
   * @param rol      rol que desempeña en el taller
   * @param contrato tipo de contrato laboral
   * @return Result con el Empleado creado si todo es válido,
   *         o Result con el error específico si algo falla
   *
   * @see Result
   * @see IErrorApp
   */
  public static Result<Empleado, IErrorApp> crear(String id, String nombre, String telefono, String email, Rol rol,
      TipoDeContrato contrato) {
    if (nombre == null || nombre.isBlank())
      return Result.error(new VerificationError("Error nombre invalido"));
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
