package com.taller.adapters.persistence.jpa.entities;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.enums.Marca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehiculo")
public class VehiculoJpaEntity {

  @Id
  @Column(name = "id_vehiculo")
  private String id;

  @Column(nullable = false, unique = true)
  private String placa;

  @ManyToOne
  @JoinColumn(name = "id_cliente", nullable = false)
  private ClienteJpaEntity cliente;

  @Column(nullable = false)
  private String modelo;

  @Column(columnDefinition = "varchar(20) not null check (marca in ('CHEVROLET', 'MAZDA', 'TOYOTA', 'RENAULT', 'KIA'))")
  private String marca;

  @Column(columnDefinition = "smallint not null check (anio >= 1886)")
  private int anio;

  protected VehiculoJpaEntity() {
  }

  public VehiculoJpaEntity(Vehiculo vehiculo) {
    // this.id = ??
    this.placa = vehiculo.getPlaca().getValue();
    this.cliente = new ClienteJpaEntity(vehiculo.getDueño());
    this.modelo = vehiculo.getModelo();
    this.marca = vehiculo.getMarca().toString();
    this.anio = vehiculo.getAnio();
  }

  public Vehiculo toDomain() {
    Marca marca = Marca.buscarPorNombre(this.marca).get();
    return Vehiculo.crear(placa, cliente.toDomain(), modelo, marca, anio).getValue();
  }
}
