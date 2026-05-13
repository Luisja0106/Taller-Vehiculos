package com.taller.adapters.persistence.jpa.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.entities.Servicio;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.interfaces.IServicio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orden")
public class OrdenJpaEntity {

  @Id
  @Column(name = "orden_id")
  private String id;

  @ManyToOne
  @JoinColumn(name = "id_vehiculo", nullable = false)
  private VehiculoJpaEntity vehiculo;

  @ManyToOne
  @JoinColumn(name = "id_mecanico", nullable = false)
  private EmpleadoJpaEntity mecanico;

  @Column(name = "fecha_entrada")
  private LocalDateTime fechaEntrada;

  @Column(name = "fecha_salida", columnDefinition = "date")
  private LocalDateTime fechaSalida;

  @Column(name = "fecha_pago", columnDefinition = "date")
  private LocalDateTime fechaPago;

  @Column(name = "valor_venta")
  private BigDecimal valorVenta;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EstadoDelTrabajo estado;

  @ManyToMany
  @JoinTable(name = "servicios_orden", joinColumns = @JoinColumn(name = "id_orden"), inverseJoinColumns = @JoinColumn(name = "id_servicio"))
  private List<ServicioJpaEntity> servicios = new ArrayList<>();

  protected OrdenJpaEntity() {
  }

  public OrdenJpaEntity(OrdenDeTrabajo orden) {
    this.id = orden.getID();
    this.vehiculo = new VehiculoJpaEntity(orden.getVehiculo());
    this.mecanico = new EmpleadoJpaEntity(orden.getEmpleadoACargo());
    this.fechaEntrada = orden.getFechaEntrada();
    this.fechaSalida = orden.getFechaDeFinalizacion();
    this.fechaPago = orden.getFechaDePago();
    this.valorVenta = orden.getValorVenta();
    this.estado = orden.getEstado();
    this.servicios = orden.getServicios().stream()
        .map(s -> new ServicioJpaEntity((Servicio) s))
        .collect(Collectors.toList());
  }

  public OrdenDeTrabajo toDomain() {
    List<IServicio> servicios = this.servicios.stream()
        .map(ServicioJpaEntity::toDomain)
        .collect(Collectors.toList());

    return OrdenDeTrabajo.reconstruir(id, vehiculo.toDomain(), mecanico.toDomain(), fechaEntrada, fechaSalida,
        fechaPago, valorVenta,
        estado, servicios);
  }
}
