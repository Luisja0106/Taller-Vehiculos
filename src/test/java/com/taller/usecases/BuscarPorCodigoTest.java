package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Cliente;
import com.taller.domain.entities.Empleado;
import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.entities.Servicio;
import com.taller.domain.entities.Vehiculo;
import com.taller.domain.repositories.IClienteRepository;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.repositories.IVehiculoRepository;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.CrearServicioCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;
import com.taller.usecases.fakes.OrdenRepositoryFake;
import com.taller.usecases.fakes.ServicioRepositoryFake;
import com.taller.usecases.fakes.VehiculoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BuscarPorCodigoTest {

  IEmpleadoRepository empleadoRepository;
  IOrdenRepository ordenRepository;
  IClienteRepository clienteRepository;
  IServicioRepository servicioRepository;
  IVehiculoRepository vehiculoRepository;
  BuscarPorCodigo useCase;
  Empleado empleado;
  OrdenDeTrabajo orden;
  Cliente cliente;
  Servicio servicio;
  Vehiculo vehiculo;

  @BeforeEach
  void setUp() {
    empleadoRepository = new EmpleadoRepositoryFake();
    ordenRepository = new OrdenRepositoryFake();
    clienteRepository = new ClienteRepositoryFake();
    servicioRepository = new ServicioRepositoryFake();
    vehiculoRepository = new VehiculoRepositoryFake();

    useCase = new BuscarPorCodigo(empleadoRepository, ordenRepository, clienteRepository, servicioRepository,
        vehiculoRepository);

    var crearEmpleadoUseCase = new ContratarEmpleado(empleadoRepository);
    empleado = crearEmpleadoUseCase
        .ejecutar(new CrearEmpleadoCMD("luis", "3208142119", "correo@correo.com", "mecanico", "fijo")).getValue();

    var crearClienteUseCase = new RegistrarCliente(clienteRepository);
    cliente = crearClienteUseCase.ejecutar(new CrearClienteCMD("Alexis", "3102790845", "correo2@correo.com"))
        .getValue();

    var crearVehiculoUseCase = new RegistrarVehiculo(vehiculoRepository, clienteRepository);
    vehiculo = crearVehiculoUseCase.ejecutar(new CrearVehiculoCMD("CYJ691", "CLI001", "clio", "Renault", 2016))
        .getValue();
    var creanOrdenUseCase = new CrearOrden(ordenRepository, vehiculoRepository, empleadoRepository);
    orden = creanOrdenUseCase.ejecutar(new CrearOrdenCMD("CYJ691", "EMP001")).getValue();

    var crearServicioUseCase = new CrearServicio(servicioRepository);
    servicio = crearServicioUseCase.ejecutar(new CrearServicioCMD("Cambio de bujia", "16000")).getValue();
  }

  @Nested
  @DisplayName("Caso Exitoso")
  class CasoExitos {

    @Test
    @DisplayName("Si el input es correcto, la busqueda es correcta")
    void inputCorrecto_BusquedaExitosa() {
      var empleadoResu = useCase.ejecutar(empleado.getId());
      var ordenResu = useCase.ejecutar(orden.getID());
      var clienteResu = useCase.ejecutar(cliente.getId());
      var servicioResu = useCase.ejecutar(servicio.getId());
      var vehiculoResu = useCase.ejecutar("VHC" + vehiculo.getPlaca());

      assertAll(
          () -> assertTrue(empleadoResu.isSuccess),
          () -> assertEquals(empleado, empleadoResu.getValue()),
          () -> assertTrue(clienteResu.isSuccess),
          () -> assertEquals(cliente, clienteResu.getValue()),
          () -> assertTrue(servicioResu.isSuccess),
          () -> assertEquals(servicio, servicioResu.getValue()),
          () -> assertTrue(ordenResu.isSuccess),
          () -> assertEquals(orden, ordenResu.getValue()),
          () -> assertTrue(vehiculoResu.isSuccess),
          () -> assertEquals(vehiculo, vehiculoResu.getValue()));

    }
  }
}
