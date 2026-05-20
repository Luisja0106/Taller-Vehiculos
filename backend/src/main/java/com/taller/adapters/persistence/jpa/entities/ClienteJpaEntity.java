package com.taller.adapters.persistence.jpa.entities;

import java.util.ArrayList;
import java.util.List;

import com.taller.domain.entities.Cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

  @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
  private List<VehiculoJpaEntity> vehiculos = new ArrayList<>();

  protected ClienteJpaEntity() {

  }

  public ClienteJpaEntity(Cliente cliente) {
    this.id = cliente.getId();
    this.nombre = cliente.getNombre();
    this.telefono = cliente.getTelefono().getValue();
    this.email = cliente.getEmail().toString();
  }

  public Cliente toDomain() {
    Cliente cliente = toBasicDomain();

    if (this.vehiculos != null) {
      this.vehiculos.forEach(v -> {
        cliente.addVehiculo(v.toDomain(cliente));
      });
    }
    return cliente;
  }

  private Cliente toBasicDomain() {
    var resultado = Cliente.crear(id, nombre, telefono, email);

    if (!resultado.isSuccess) {
      throw new IllegalStateException("Error reconstruyendo el cliente" + resultado.getError());
    }

    return resultado.getValue();
  }

}
