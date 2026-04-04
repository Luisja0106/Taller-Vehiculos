package com.taller.usecases;

import com.taller.domain.enums.TipoDeEntidad;
import com.taller.domain.errors.VerificationError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.interfaces.ResultadoBusqueda;
import com.taller.domain.repositories.*;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;

public class BuscarPorCodigo {

  private final IEmpleadoRepository empleadoRepository;
  private final IOrdenRepository ordenRepository;
  private final IClienteRepository clienteRepository;
  private final IServicioRepository servicioRepository;
  private final IVehiculoRepository vehiculoRepository;

  public BuscarPorCodigo(IEmpleadoRepository empleadoRepository, IOrdenRepository ordenRepository,
      IClienteRepository clienteRepository, IServicioRepository servicioRepository,
      IVehiculoRepository vehiculoRepository) {
    this.empleadoRepository = empleadoRepository;
    this.ordenRepository = ordenRepository;
    this.clienteRepository = clienteRepository;
    this.servicioRepository = servicioRepository;
    this.vehiculoRepository = vehiculoRepository;
  }

  public Result<ResultadoBusqueda, IErrorApp> ejecutar(String codigo) {
    if (codigo == null || codigo.isEmpty())
      return Result.error(new VerificationError("El codigo no puede estar vacio"));

    var tipoDeCodigo = TipoDeEntidad.buscarPorPrefijo(codigo);

    if (tipoDeCodigo.isEmpty()) {
      return Result.error(new VerificationError("codigo no identificado"));
    }
    return switch (tipoDeCodigo.get()) {
      case EMP -> busquedaEmpleado(codigo);
      case CLI -> busquedaCliente(codigo);
      case SRV -> busquedaServicio(codigo);
      case ORD -> busquedaOrden(codigo);
      case VHC -> busquedaVehiculo(codigo);
    };
  }

  private Result<ResultadoBusqueda, IErrorApp> busquedaEmpleado(String id) {
    var empleado = empleadoRepository.buscarPorId(id);

    if (empleado.isEmpty()) {
      return Result.error(new VerificationError("Error empleado no encontrado"));
    }
    return Result.success(new ResultadoBusqueda.EmpleadoEncontrado(empleado.get()));
  }

  private Result<ResultadoBusqueda, IErrorApp> busquedaCliente(String id) {
    var cliente = clienteRepository.buscarPorId(id);

    if (cliente.isEmpty()) {
      return Result.error(new VerificationError("Error cliente no encontrado"));
    }
    return Result.success(new ResultadoBusqueda.ClienteEncontrado(cliente.get()));
  }

  private Result<ResultadoBusqueda, IErrorApp> busquedaOrden(String id) {
    var orden = ordenRepository.buscarPorId(id);

    if (orden.isEmpty()) {
      return Result.error(new VerificationError("Error orden no encontrada"));
    }
    return Result.success(new ResultadoBusqueda.OrdenEncontrada(orden.get()));
  }

  private Result<ResultadoBusqueda, IErrorApp> busquedaServicio(String id) {
    var servicio = servicioRepository.buscarPorId(id);

    if (servicio.isEmpty()) {
      return Result.error(new VerificationError("Error servicio no encontrado"));
    }
    return Result.success(new ResultadoBusqueda.ServicioEncontrado(servicio.get()));
  }

  private Result<ResultadoBusqueda, IErrorApp> busquedaVehiculo(String codigo) {
    String placaRaw = codigo.substring(3);
    var placa = Placa.crear(placaRaw);

    if (!placa.isSuccess) {
      return Result.error(placa.getError());
    }
    var vehiculo = vehiculoRepository.buscarPorPlaca(placa.getValue());

    if (vehiculo.isEmpty()) {
      return Result.error(new VerificationError("Error vehiculo no encontrado"));
    }
    return Result.success(new ResultadoBusqueda.VehiculoEncontrado(vehiculo.get()));
  }

}
