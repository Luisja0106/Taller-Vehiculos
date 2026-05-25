package com.taller.domain.entities;

import com.taller.domain.enums.Marca;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;

/**
 * Representa un vehículo registrado en el taller.
 *
 * Un vehículo se identifica únicamente por su placa, que actúa como
 * identificador natural de negocio. Siempre pertenece a un {@link Cliente}
 * y no puede existir sin uno.
 *
 * <p>
 * Solo puede crearse a través del factory method {@link #crear}, que
 * garantiza que ninguna instancia de esta clase contiene datos inválidos.
 * </p>
 *
 * @see Cliente
 * @see Placa
 * @see Marca
 */
public class Vehiculo {
  private final Placa placa;
  private Cliente dueño;
  private String modelo;
  private Marca marca;
  private int anio;

  private Vehiculo(Placa placa, Cliente dueño, String modelo, Marca marca, int anio) {
    this.placa = placa;
    this.dueño = dueño;
    this.modelo = modelo;
    this.marca = marca;
    this.anio = anio;
  }

  /**
   * Crea un vehículo validando todos sus datos antes de construirlo.
   *
   * <p>
   * Valida que la placa tenga formato correcto, que el dueño no sea nulo,
   * que la marca sea válida, que el modelo no esté vacío y que el año sea
   * positivo. Si alguna validación falla retorna un error descriptivo
   * sin crear el objeto.
   * </p>
   *
   * @param placa  placa del vehículo en formato colombiano, ej: "ABC123"
   * @param dueño  cliente propietario del vehículo, no puede ser nulo
   * @param modelo modelo del vehículo, ej: "Corolla", no puede estar vacío
   * @param marca  marca del vehículo, debe ser un valor válido de {@link Marca}
   * @param anio   año de fabricación, debe ser mayor a cero
   * @return {@link Result} con el vehículo creado si todo es válido,
   *         o {@link Result} con el error específico si algo falla
   */
  public static Result<Vehiculo, IErrorApp> crear(String placa, Cliente dueño, String modelo, Marca marca, int anio) {
    var placaVO = Placa.crear(placa);

    if (!placaVO.isSuccess) {
      return Result.error(placaVO.getError());
    }
    if (dueño == null) {
      return Result.error(new VerificationError("El usuario es invalido"));
    }
    if (marca == null) {
      return Result.error(new VerificationError("La marca es invalida"));
    }
    if (modelo == null || modelo.isBlank()) {
      return Result.error(new VerificationError("El modelo no puede estar vacio"));
    }
    if (anio <= 0) {
      return Result.error(new VerificationError("El año es invalido"));
    }
    return Result.success(new Vehiculo(placaVO.getValue(), dueño, modelo, marca, anio));
  }

  public Placa getPlaca() {
    return this.placa;
  }

  public Cliente getDueño() {
    return dueño;
  }

  public String getModelo() {
    return modelo;
  }

  public String getMarca() {
    return marca.toString();
  }

  public Marca getMarcaEnum() {
    return marca;
  }

  /**
   * Reasigna el vehículo a un nuevo dueño y sincroniza la relación
   * agregando este vehículo a la lista del nuevo cliente.
   *
   * <p>
   * Si el nuevo dueño es nulo o es el mismo que el actual,
   * la operación no tiene efecto.
   * </p>
   *
   * @param nuevoDueño el nuevo cliente propietario, ignorado si es nulo
   */
  public void cambiarDueño(Cliente nuevoDueño) {
    if (nuevoDueño == null || nuevoDueño.equals(this.dueño))
      return;
    if (this.dueño != null) {
      this.dueño.removeVehiculo(this);
    }
    this.dueño = nuevoDueño;
    nuevoDueño.addVehiculo(this);
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public void setMarca(Marca marca) {
    this.marca = marca;
  }

  public int getAnio() {
    return anio;
  }

  public void setAnio(int anio) {
    if (anio <= 0)
      return;
    this.anio = anio;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Vehiculo))
      return false;
    Vehiculo otro = (Vehiculo) obj;
    return this.placa.equals(otro.placa);
  }

  @Override
  public int hashCode() {
    return this.placa.hashCode();
  }

}
