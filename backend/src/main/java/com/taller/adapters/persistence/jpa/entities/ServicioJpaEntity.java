package com.taller.adapters.persistence.jpa.entities;

import java.math.BigDecimal;

import com.taller.domain.entities.Servicio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicio")
public class ServicioJpaEntity {

  @Id
  @Column(name = "servicio_id")
  private String id;

  @Column(nullable = false, unique = true)
  private String nombre;

  @Column(nullable = false)
  private BigDecimal precio;

  protected ServicioJpaEntity() {
  }

  public ServicioJpaEntity(Servicio servicio) {
    this.id = servicio.getId();
    this.nombre = servicio.getNombreDelServicio();
    this.precio = servicio.getPrecio();
  }

  public Servicio toDomain() {
    return Servicio.crear(id, nombre, precio).getValue();
  }
}
