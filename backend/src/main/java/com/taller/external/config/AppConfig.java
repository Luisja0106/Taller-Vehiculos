package com.taller.external.config;

import com.taller.adapters.persistence.jpa.implementations.ClienteRepositoryImpl;
import com.taller.adapters.persistence.jpa.implementations.EmpleadoRepositoryImpl;
import com.taller.adapters.persistence.jpa.implementations.OrdenRepositoryImpl;
import com.taller.adapters.persistence.jpa.implementations.ServicioRepositoryImpl;
import com.taller.adapters.persistence.jpa.implementations.VehiculoRepositoryImpl;
import com.taller.adapters.persistence.jpa.repositories.ClienteJpaRepository;
import com.taller.adapters.persistence.jpa.repositories.EmpleadoJpaRepository;
import com.taller.adapters.persistence.jpa.repositories.OrdenJpaRepository;
import com.taller.adapters.persistence.jpa.repositories.ServicioJpaRepository;
import com.taller.adapters.persistence.jpa.repositories.VehiculoJpaRepository;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.usecases.ActualizarCliente;
import com.taller.usecases.ActualizarEmpleado;
import com.taller.usecases.ActualizarServicio;
import com.taller.usecases.ActualizarVehiculo;
import com.taller.usecases.AgregarServicio;
import com.taller.usecases.AvanzarEstadoDeOrden;
import com.taller.usecases.BuscarPorCodigo;
import com.taller.usecases.ContratarEmpleado;
import com.taller.usecases.CrearOrden;
import com.taller.usecases.CrearServicio;
import com.taller.usecases.ListarClientes;
import com.taller.usecases.ListarEmpleados;
import com.taller.usecases.ListarOrdenes;
import com.taller.usecases.ListarServicios;
import com.taller.usecases.ListarVehiculos;
import com.taller.usecases.ReasignarEmpleadoAOrden;
import com.taller.usecases.RegistrarCliente;
import com.taller.usecases.RegistrarPago;
import com.taller.usecases.RegistrarVehiculo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  // NOTE: repos
  @Bean
  public IClienteRepository clienteRepository(ClienteJpaRepository jpa) {
    return new ClienteRepositoryImpl(jpa);
  }

  @Bean
  public IEmpleadoRepository empleadoRepository(EmpleadoJpaRepository jpa) {
    return new EmpleadoRepositoryImpl(jpa);
  }

  @Bean
  public IVehiculoRepository vehiculoRepository(VehiculoJpaRepository jpa) {
    return new VehiculoRepositoryImpl(jpa);
  }

  @Bean
  public IServicioRepository servicioRepository(ServicioJpaRepository jpa) {
    return new ServicioRepositoryImpl(jpa);
  }

  @Bean
  IOrdenRepository ordenRepository(OrdenJpaRepository jpa) {
    return new OrdenRepositoryImpl(jpa);
  }

  // NOTE: use cases

  @Bean
  public ContratarEmpleado contratarEmpleado(IEmpleadoRepository repo) {
    return new ContratarEmpleado(repo);
  }

  @Bean
  public ActualizarEmpleado actualizarEmpleado(IEmpleadoRepository repo) {
    return new ActualizarEmpleado(repo);
  }

  @Bean
  public RegistrarCliente registrarCliente(IClienteRepository repo) {
    return new RegistrarCliente(repo);
  }

  @Bean
  public ActualizarCliente actualizarCliente(IClienteRepository repo) {
    return new ActualizarCliente(repo);
  }

  @Bean
  public RegistrarVehiculo registrarVehiculo(IVehiculoRepository vehiculoRepo, IClienteRepository clienteRepo) {
    return new RegistrarVehiculo(vehiculoRepo, clienteRepo);
  }

  @Bean
  public ActualizarVehiculo actualizarVehiculo(IVehiculoRepository vehiculoRepo, IClienteRepository clienteRepo) {
    return new ActualizarVehiculo(vehiculoRepo, clienteRepo);
  }

  @Bean
  public CrearServicio crearServicio(IServicioRepository repo) {
    return new CrearServicio(repo);
  }

  @Bean
  public ActualizarServicio actualizarServicio(IServicioRepository repo) {
    return new ActualizarServicio(repo);
  }

  @Bean
  public CrearOrden crearOrden(IOrdenRepository ordenRepo, IVehiculoRepository vehiculoRepo,
      IEmpleadoRepository empleadoRepo) {
    return new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);
  }

  @Bean
  public AgregarServicio agregarServicio(IOrdenRepository ordenRepo, IServicioRepository servicoRepo) {
    return new AgregarServicio(servicoRepo, ordenRepo);
  }

  @Bean
  public AvanzarEstadoDeOrden avanzarEstadoDeOrden(IOrdenRepository ordenRepo) {
    return new AvanzarEstadoDeOrden(ordenRepo);
  }

  @Bean
  public RegistrarPago registrarPago(IOrdenRepository repo) {
    return new RegistrarPago(repo);
  }

  @Bean
  public ReasignarEmpleadoAOrden reasignarEmpleadoAOrden(IOrdenRepository ordenRepository,
      IEmpleadoRepository empleadoRepository) {
    return new ReasignarEmpleadoAOrden(empleadoRepository, ordenRepository);
  }

  @Bean
  public BuscarPorCodigo buscarPorCodigo(IEmpleadoRepository empleadoRepository, IClienteRepository clienteRepository,
      IVehiculoRepository vehiculoRepository, IServicioRepository servicioRepository,
      IOrdenRepository ordenRepository) {
    return new BuscarPorCodigo(empleadoRepository, ordenRepository, clienteRepository, servicioRepository,
        vehiculoRepository);
  }

  @Bean
  public ListarEmpleados listarEmpleados(IEmpleadoRepository repo) {
    return new ListarEmpleados(repo);
  }

  @Bean
  public ListarClientes listarClientes(IClienteRepository repo) {
    return new ListarClientes(repo);
  }

  @Bean
  public ListarVehiculos listarVehiculos(IVehiculoRepository repo) {
    return new ListarVehiculos(repo);
  }

  @Bean
  public ListarServicios listarServicios(IServicioRepository repo) {
    return new ListarServicios(repo);
  }

  @Bean
  public ListarOrdenes listarOrdenes(IOrdenRepository repo) {
    return new ListarOrdenes(repo);
  }

}
