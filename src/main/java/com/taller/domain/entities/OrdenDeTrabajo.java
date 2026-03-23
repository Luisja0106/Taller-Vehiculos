package com.taller.domain.entities;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.interfaces.IServicio;
import com.taller.domain.utils.Result;

/**
 * Un Orden de Trabajo es un objeto que contiene la informacion de un trabajo en
 * el taller.
 *
 * Una orden de trabajo no puede existir sin, un vehiculo, un empleado a cargo,
 *
 * Se crea unicamente mediante la factory method {@link #crear}
 *
 * @see Vehiculo
 * @see Empleado
 */

public class OrdenDeTrabajo {
  private final String ID;
  private final Vehiculo vehiculo;
  private Empleado empleadoACargo; // NOTE: posibilidad de hacerlo una list para agregar mas empleados
  private final List<IServicio> servicios;
  private final LocalDateTime fechaEntrada;
  private LocalDateTime fechaDeFinalizacion;
  private LocalDateTime fechaDePago;
  private BigDecimal valorVenta;
  private EstadoDelTrabajo estado;

  private OrdenDeTrabajo(String id, Vehiculo vehiculo, Empleado empleadoACargo) {
    ID = id;
    this.vehiculo = vehiculo;
    this.empleadoACargo = empleadoACargo;
    this.fechaEntrada = LocalDateTime.now();
    servicios = new ArrayList<>();
    this.estado = EstadoDelTrabajo.PENDIENTE;
  }

  /**
   * Crea una orden de trabajo validando que todos sus datos sean validos.
   *
   * valida que su id no sea null, ni vacio, y que el vehiculo no sea null ni el
   * empleado y retorna un Result con un vehiculo si todo es satisfactorio o un
   * error si no, define automaticamente la fecha de entrada del vehiculo.
   *
   * @param id       id unico del objeto
   * @param vehiculo vehiculo al cual se le hara la revision
   * @param empleado empleado a cargo del trabajo
   * @return un result con el vehiculo o un error describiendo que fallo
   *
   * @see Result
   * @see IErrorApp
   */
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

  public Result<Void, IErrorApp> registrarPago(BigDecimal pago) {
    if (this.estado != EstadoDelTrabajo.EN_ESPERA_DE_PAGO)
      return Result.error(new VerificationError("Error el estado no admite pago"));
    if (pago == null)
      return Result.error(new VerificationError("Error el valor de venta no puede ser nulo"));
    if (pago.compareTo(BigDecimal.ZERO) <= 0)
      return Result.error(new VerificationError("Error el valor de venta no puede ser negativo"));

    valorVenta = pago;
    return Result.success(null);
  }

  public BigDecimal getValorVenta() {
    return valorVenta;
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

  public LocalDateTime getFechaDeFinalizacion() {
    return fechaDeFinalizacion;
  }

  public LocalDateTime getFechaDePago() {
    return fechaDePago;
  }

  public EstadoDelTrabajo getEstado() {
    return estado;
  }

  /**
   * Metodo que verifica que sea valido y en un estado viable añadir un servicio.
   *
   *
   * Retorna void o un error descriptivo
   *
   * @param servicio servicio que se desea añadir
   */
  public Result<Void, IErrorApp> addServicio(IServicio servicio) {
    if (servicio == null) {
      return Result.error(new VerificationError("El servicio no puede ser nulo"));
    }
    if ((this.estado == EstadoDelTrabajo.FINALIZADO) || (this.estado == EstadoDelTrabajo.EN_ESPERA_DE_PAGO)) {
      return Result.error(new VerificationError("No se pueden añadir servicios a un Trabajo terminado"));
    }
    servicios.add(servicio);
    return Result.success(null);
  }

  /**
   * Avanza la orden al siguiente estado en el flujo de trabajo.
   *
   * El flujo es:
   * PENDIENTE → EN_PROCESO → EN_ESPERA_DE_PAGO → FINALIZADO
   *
   * Registra automáticamente la fecha de finalización al pasar a
   * EN_ESPERA_DE_PAGO, y la fecha de pago al pasar a FINALIZADO.
   *
   * @return Result vacío si el avance fue exitoso,
   *         o error si la orden ya está finalizada
   */
  public Result<Void, IErrorApp> avanzarEstado() {
    return switch (this.estado) {
      case PENDIENTE -> {
        this.estado = EstadoDelTrabajo.EN_PROCESO;
        yield Result.success(null);
      }
      case EN_PROCESO -> {
        this.estado = EstadoDelTrabajo.EN_ESPERA_DE_PAGO;
        this.fechaDeFinalizacion = LocalDateTime.now();
        yield Result.success(null);
      }
      case EN_ESPERA_DE_PAGO -> {
        this.estado = EstadoDelTrabajo.FINALIZADO;
        this.fechaDePago = LocalDateTime.now();
        yield Result.success(null);
      }
      case FINALIZADO -> Result.error(new VerificationError("La orden ya esta finalizada"));
    };
  }
}
