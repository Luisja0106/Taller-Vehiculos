package com.taller.adapters.persistence.jpa.entities;

import com.taller.domain.entities.Cliente;
import com.taller.domain.entities.Vehiculo;
import com.taller.domain.enums.Marca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehiculo")
public class VehiculoJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_vehiculo")
  private Long id;

  @Column(nullable = false, unique = true)
  private String placa;

  @ManyToOne
  @JoinColumn(name = "id_cliente", nullable = false)
  private ClienteJpaEntity cliente;

  @Column(nullable = false)
  private String modelo;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Marca marca;

  @Column(columnDefinition = "smallint not null check (anio >= 1886)")
  private int anio;

  protected VehiculoJpaEntity() {
  }

  public VehiculoJpaEntity(Vehiculo vehiculo) {
    this.placa = vehiculo.getPlaca().getValue();
    this.cliente = new ClienteJpaEntity(vehiculo.getDueño());
    this.modelo = vehiculo.getModelo();
    this.marca = vehiculo.getMarcaEnum();
    this.anio = vehiculo.getAnio();
  }

  public Vehiculo toDomain() {
    return Vehiculo.crear(placa, cliente.toDomain(), modelo, marca, anio).getValue();
  }

  public Vehiculo toDomain(Cliente duenio) {
    var resultado = Vehiculo.crear(this.placa, duenio, this.modelo, this.marca, this.anio);

    if (!resultado.isSuccess) {
      throw new IllegalStateException("Error reconstruyendo el vehiculo" + resultado.getError());
    }

    return resultado.getValue();
  }
}
