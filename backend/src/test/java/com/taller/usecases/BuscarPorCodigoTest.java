package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Cliente;
import com.taller.domain.entities.Empleado;
import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.entities.Servicio;
import com.taller.domain.entities.Vehiculo;
import com.taller.domain.interfaces.ResultadoBusqueda;
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
import org.junit.jupiter.params.provider.NullSource;
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
  class CasoExitoso {

    @Test
    @DisplayName("Si el input es correcto, la busqueda es correcta")
    void inputCorrecto_BusquedaExitosa() {
      var empleadoResu = useCase.ejecutar(empleado.getId());
      var ordenResu = useCase.ejecutar(orden.getID());
      var clienteResu = useCase.ejecutar(cliente.getId());
      var servicioResu = useCase.ejecutar(servicio.getId());
      var vehiculoResu = useCase.ejecutar("VHC" + vehiculo.getPlaca().getValue());

      assertAll(
          () -> assertTrue(empleadoResu.isSuccess),
          () -> {
            var resultado = assertInstanceOf(ResultadoBusqueda.EmpleadoEncontrado.class, empleadoResu.getValue());
            assertEquals(empleado, resultado.empleado());
          },
          () -> assertTrue(clienteResu.isSuccess),
          () -> {
            var resultado = assertInstanceOf(ResultadoBusqueda.ClienteEncontrado.class, clienteResu.getValue());
            assertEquals(cliente, resultado.cliente());
          },
          () -> assertTrue(servicioResu.isSuccess),
          () -> {
            var resultado = assertInstanceOf(ResultadoBusqueda.ServicioEncontrado.class, servicioResu.getValue());
            assertEquals(servicio, resultado.servicio());
          },
          () -> assertTrue(ordenResu.isSuccess),
          () -> {
            var resultado = assertInstanceOf(ResultadoBusqueda.OrdenEncontrada.class, ordenResu.getValue());
            assertEquals(orden, resultado.orden());
          },
          () -> assertTrue(vehiculoResu.isSuccess),
          () -> {
            var resultado = assertInstanceOf(ResultadoBusqueda.VehiculoEncontrado.class, vehiculoResu.getValue());
            assertEquals(vehiculo, resultado.vehiculo());
          });
    }
  }

  @Nested
  @DisplayName("Caso Erroneo")
  class CasoErroneo {

    @Test
    @DisplayName("Si el input es nulo, retorna error")
    void inputNulo_RetornaError() {
      var resultado = useCase.ejecutar(null);
      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("El codigo no puede estar vacio", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "codigo invalido" })
    @DisplayName("Si el input es invalido retorna error")
    void inputInvalido_RetornaError(String input) {
      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @Test
    @DisplayName("Si el prefijo del codigo es invalido, retorna error")
    void prefijoInvalido_RetornaError() {
      String input = "COD001";
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("codigo no identificado", resultado.getError().getMessage()));
    }

    @Test
    @DisplayName("Si la entidad no existe, retorna Error")
    void entidadInexistente_RetornaError() {
      var empleadoResu = useCase.ejecutar("EMP002");
      var ordenResu = useCase.ejecutar(orden.getID() + "1");
      var clienteResu = useCase.ejecutar("CLI002");
      var servicioResu = useCase.ejecutar("SRV002");
      var vehiculoResu = useCase.ejecutar("VHC" + "CYJ692");

      assertAll(
          () -> assertFalse(empleadoResu.isSuccess),
          () -> assertFalse(ordenResu.isSuccess),
          () -> assertFalse(clienteResu.isSuccess),
          () -> assertFalse(servicioResu.isSuccess),
          () -> assertFalse(vehiculoResu.isSuccess));
    }
  }
}
