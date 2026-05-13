package com.taller.adapters.persistence.jpa.entities;

import com.taller.domain.entities.Empleado;
import com.taller.domain.enums.Rol;
import com.taller.domain.enums.TipoDeContrato;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleado")
public class EmpleadoJpaEntity {

  @Id
  @Column(name = "id_empleado")
  private String id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false, unique = true)
  private String telefono;

  @Column(nullable = false, unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Rol rol;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_contrato", nullable = false)
  private TipoDeContrato contrato;

  protected EmpleadoJpaEntity() {

  }

  public EmpleadoJpaEntity(Empleado empleado) {
    this.id = empleado.getId();
    this.nombre = empleado.getNombre();
    this.telefono = empleado.getTelefono().getValue();
    this.email = empleado.getEmail().toString();
    this.rol = empleado.getRol();
    this.contrato = empleado.getContrato();
  }

  public Empleado toDomain() {
    return Empleado.crear(id, nombre, telefono, email, rol, contrato).getValue();
  }
}
