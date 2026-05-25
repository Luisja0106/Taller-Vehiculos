package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.dto.RegistrarPagoCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;
import com.taller.usecases.fakes.OrdenRepositoryFake;
import com.taller.usecases.fakes.VehiculoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class RegistrarPagoTest {

  IOrdenRepository ordenRepo;
  RegistrarPago useCase;
  OrdenDeTrabajo orden;

  @BeforeEach
  void setUp() {
    ordenRepo = new OrdenRepositoryFake();
    useCase = new RegistrarPago(ordenRepo);

    var clienteRepo = new ClienteRepositoryFake();
    var crearClienteUseCase = new RegistrarCliente(clienteRepo);
    crearClienteUseCase.ejecutar(new CrearClienteCMD("Luis", "3102790845", "correo@correo.com"));

    var vehiculoRepo = new VehiculoRepositoryFake();
    var crearVehiculoRepo = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    crearVehiculoRepo.ejecutar(new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "chevrolet", 2015));

    var empleadoRepo = new EmpleadoRepositoryFake();
    var crearEmpleadoUseCase = new ContratarEmpleado(empleadoRepo);
    crearEmpleadoUseCase
        .ejecutar(new CrearEmpleadoCMD("Alexis", "3208142119", "correo2@correo.com", "mecanico", "fijo"));

    var crearOrdenUseCase = new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);
    orden = crearOrdenUseCase.ejecutar(new CrearOrdenCMD("CYJ691", "EMP001")).getValue();

    orden.avanzarEstado(); // en proceso
    orden.avanzarEstado(); // en espera de pago
  }

  @Nested
  @DisplayName("Caso Exitoso")
  class CasoExitoso {

    @Test
    @DisplayName("Si el input es correcto, debe registrar")
    void inputCorrecto_Registra() {
      var input = new RegistrarPagoCMD(orden.getID(), "150000");
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(new BigDecimal(input.pago()), orden.getValorVenta()));
    }

    @Test
    @DisplayName("Si el proceso es correcto, se actualiza en el repositorio")
    void registroCorrecto_ActualizaRepositorio() {
      var input = new RegistrarPagoCMD(orden.getID(), "150000");
      var resultado = useCase.ejecutar(input);
      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(new BigDecimal(input.pago()), orden.getValorVenta()),
          () -> assertEquals(new BigDecimal(input.pago()), ordenRepo.buscarPorId(orden.getID()).get().getValorVenta()));
    }
  }

  @Nested
  @DisplayName("Caso Erroneo")
  class CasoErroneo {

    @Test
    @DisplayName("Si el input es nulo, debe retornar error")
    void inputNulo_RetornaError() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error los datos no pueden ser nulos", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "ORD999", "ordenInvalida" })
    @DisplayName("Si la orden no existe, debe retornar error")
    void ordenInexistente_RetornaError(String ordenId) {
      var input = new RegistrarPagoCMD(ordenId, "150000");
      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "precioInvalido", "abc123", "-1" })
    @DisplayName("Si el precio es invalido, debe retornar error")
    void precioInvalido_RetornaError(String precio) {
      var input = new RegistrarPagoCMD(orden.getID(), precio);
      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @Test
    @DisplayName("Si la orden no esta en espera de pago, debe retornar error")
    void estadoInvalido_RetornaError() {
      // Crear una orden nueva que queda en estado PENDIENTE
      var clienteRepo2 = new ClienteRepositoryFake();
      var registrarCliente2 = new RegistrarCliente(clienteRepo2);
      registrarCliente2.ejecutar(new CrearClienteCMD("Ana", "3008142119", "ana@correo.com"));

      var vehiculoRepo2 = new VehiculoRepositoryFake();
      var registrarVehiculo2 = new RegistrarVehiculo(vehiculoRepo2, clienteRepo2);
      registrarVehiculo2.ejecutar(new CrearVehiculoCMD("ABC123", "CLI001", "Spark", "Chevrolet", 2020));

      var empleadoRepo2 = new EmpleadoRepositoryFake();
      var contratarEmpleado2 = new ContratarEmpleado(empleadoRepo2);
      contratarEmpleado2.ejecutar(new CrearEmpleadoCMD("Pedro", "3108142119", "pedro@correo.com", "mecanico", "fijo"));

      var ordenRepo2 = new OrdenRepositoryFake();
      var crearOrden2 = new CrearOrden(ordenRepo2, vehiculoRepo2, empleadoRepo2);
      var ordenPendiente = crearOrden2.ejecutar(new CrearOrdenCMD("ABC123", "EMP001")).getValue();

      var useCasePago = new RegistrarPago(ordenRepo2);
      var input = new RegistrarPagoCMD(ordenPendiente.getID(), "150000");
      var resultado = useCasePago.ejecutar(input);

      assertAll(
          () -> assertEquals(EstadoDelTrabajo.PENDIENTE, ordenPendiente.getEstado()),
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error el estado no admite pago", resultado.getError().getMessage()));
    }
  }

}
