package com.taller.adapters.persistence.jpa.entities;

import com.taller.domain.entities.Cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class ClienteJpaEntity {

  @Id
  @Column(name = "id_cliente")
  private String id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false, unique = true)
  private String telefono;

  @Column(nullable = false, unique = true)
  private String email;

  protected ClienteJpaEntity() {

  }

  public ClienteJpaEntity(Cliente cliente) {
    this.id = cliente.getId();
    this.nombre = cliente.getNombre();
    this.telefono = cliente.getTelefono().getValue();
    this.email = cliente.getEmail().toString();
  }

  public Cliente toDomain() {
    return Cliente.crear(id, nombre, telefono, email).getValue();
  }

}
