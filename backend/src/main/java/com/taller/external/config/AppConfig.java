package com.taller.external.config;

import com.taller.adapters.persistence.jpa.implementations.*;
import com.taller.adapters.persistence.jpa.repositories.*;
import com.taller.domain.repositories.*;
import com.taller.usecases.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

  // NOTE: repos
  @Bean
  @Primary
  public IClienteRepository clienteRepository(ClienteJpaRepository jpa) {
    return new ClienteRepositoryImpl(jpa);
  }

  @Bean
  @Primary
  public IEmpleadoRepository empleadoRepository(EmpleadoJpaRepository jpa) {
    return new EmpleadoRepositoryImpl(jpa);
  }

  @Bean
  @Primary
  public IVehiculoRepository vehiculoRepository(VehiculoJpaRepository jpa) {
    return new VehiculoRepositoryImpl(jpa);
  }

  @Bean
  @Primary
  public IServicioRepository servicioRepository(ServicioJpaRepository jpa) {
    return new ServicioRepositoryImpl(jpa);
  }

  @Bean
  @Primary
  public IOrdenRepository ordenRepository(OrdenJpaRepository jpaRepo, VehiculoJpaRepository vehiculoJpaRepo,
      EmpleadoJpaRepository empleadoJpaRepo) {
    return new OrdenRepositoryImpl(jpaRepo, vehiculoJpaRepo, empleadoJpaRepo);
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

  @Bean
  public BuscarClientePorId buscarClientePorId(IClienteRepository repo) {
    return new BuscarClientePorId(repo);
  }

  @Bean
  public BuscarEmpleadoPorId buscarEmpleadoPorId(IEmpleadoRepository repo) {
    return new BuscarEmpleadoPorId(repo);
  }

  @Bean
  public BuscarServicioPorId buscarServicioPorId(IServicioRepository repo) {
    return new BuscarServicioPorId(repo);
  }

  @Bean
  public BuscarOrdenPorId buscarOrdenPorId(IOrdenRepository repo) {
    return new BuscarOrdenPorId(repo);
  }

  @Bean
  public BuscarVehiculoPorPlaca buscarVehiculoPorPlaca(IVehiculoRepository repo) {
    return new BuscarVehiculoPorPlaca(repo);
  }

}
