package com.taller.core.entities;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.taller.core.enums.EstadoDelServicio;
import com.taller.core.interfaces.IServicio;

public class OrdenDeServicio {
  private final String ID;
  private final Vehiculo vehiculo;
  private Empleado empleadoACargo; // NOTE: posibilidad de hacerlo una list para agregar mas empleados
  private final List<IServicio> servicios;
  private final LocalDateTime fechaEntrada;
  private LocalDateTime fechaSalida;
  private EstadoDelServicio estado;

  public OrdenDeServicio(String id, Vehiculo vehiculo, Empleado empleadoACargo) {
    ID = id;
    this.vehiculo = vehiculo;
    this.empleadoACargo = empleadoACargo;
    this.fechaEntrada = LocalDateTime.now();
    servicios = new ArrayList<>();
    this.estado = EstadoDelServicio.PENDIENTE;
  }

  public String getID() {
    return ID;
  }

  public Vehiculo getVehiculo() {
    return vehiculo;
  }

  public Empleado getEmpleadoACargo() {
    return empleadoACargo;
  }

  public List<IServicio> getServicios() {
    return new ArrayList<IServicio>(this.servicios);
  }

  public LocalDateTime getFechaEntrada() {
    return fechaEntrada;
  }

  public LocalDateTime getFechaSalida() {
    return fechaSalida;
  }

  public String getEstado() {
    return estado.toString();
  }

  public void addServicio(IServicio servicio) {
    servicios.add(servicio);
  }

  // metodo para la maquina de estados
  private void Terminado() {
    fechaSalida = LocalDateTime.now();
  }

}
