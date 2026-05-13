package com.taller.adapters.persistence.jpa.entities;

import com.taller.domain.entities.Empleado;
import com.taller.domain.enums.Rol;
import com.taller.domain.enums.TipoDeContrato;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @Column(columnDefinition = "varchar(20) not null check (rol in ( 'MECANICO', 'ADMINISTRADOR'))")
  private String rol;

  @Column(columnDefinition = "varchar(20) not null check (tipo_contrato in ('FIJO','PARCIAL'))")
  private String tipo_contrato;

  protected EmpleadoJpaEntity() {

  }

  public EmpleadoJpaEntity(Empleado empleado) {
    this.id = empleado.getId();
    this.nombre = empleado.getNombre();
    this.telefono = empleado.getTelefono().getValue();
    this.email = empleado.getEmail().toString();
    this.rol = empleado.getRol().toString();
    this.tipo_contrato = empleado.getContrato().toString();
  }

  public Empleado toDomain() {
    Rol rol = Rol.buscarPorNombre(this.rol).get();
    TipoDeContrato contrato = TipoDeContrato.buscarPorNombre(this.tipo_contrato).get();
    return Empleado.crear(id, nombre, telefono, email, rol, contrato).getValue();
  }
}
