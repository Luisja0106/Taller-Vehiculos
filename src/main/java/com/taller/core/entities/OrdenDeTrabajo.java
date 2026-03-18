package com.taller.core.entities;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.taller.core.enums.EstadoDelTrabajo;
import com.taller.core.error.VerificationError;
import com.taller.core.interfaces.IErrorApp;
import com.taller.core.interfaces.IServicio;
import com.taller.core.utils.Result;

public class OrdenDeTrabajo {
  private final String ID;
  private final Vehiculo vehiculo;
  private Empleado empleadoACargo; // NOTE: posibilidad de hacerlo una list para agregar mas empleados
  private final List<IServicio> servicios;
  private final LocalDateTime fechaEntrada;
  private LocalDateTime fechaSalida;
  private EstadoDelTrabajo estado;

  private OrdenDeTrabajo(String id, Vehiculo vehiculo, Empleado empleadoACargo) {
    ID = id;
    this.vehiculo = vehiculo;
    this.empleadoACargo = empleadoACargo;
    this.fechaEntrada = LocalDateTime.now();
    servicios = new ArrayList<>();
    this.estado = EstadoDelTrabajo.PENDIENTE;
  }

  public static Result<OrdenDeTrabajo, IErrorApp> crear(String id, Vehiculo vehiculo, Empleado empleado) {
    if (id == null || id.isBlank()) {
      return Result.error(new VerificationError("Error el id no puede ser vacio"));
    }
    if (vehiculo == null) {
      return Result.error(new VerificationError("Error el vehiculo es invalido"));
    }
    if (empleado == null) {
      return Result.error(new VerificationError("Error el empleado a cargo no puede ser nulo"));
    }

    return Result.success(new OrdenDeTrabajo(id, vehiculo, empleado));
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

  public Result<Void, IErrorApp> addServicio(IServicio servicio) {
    if (servicio == null) {
      return Result.error(new VerificationError("El servicio no puede ser nulo"));
    }
    if (this.estado == EstadoDelTrabajo.FINALIZADO) {
      return Result.error(new VerificationError("No se pueden añadir servicios a un Trabajo terminado"));
    }
    servicios.add(servicio);
    return Result.success(null);
  }

  // metodo para la maquina de estados
  private void Terminado() {
    fechaSalida = LocalDateTime.now();
  }

}
