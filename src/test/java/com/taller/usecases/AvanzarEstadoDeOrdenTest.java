package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.usecases.dto.AvanzarEstadoDeOrdenCMD;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
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

class AvanzarEstadoDeOrdenTest {
  OrdenRepositoryFake ordenRepo;
  AvanzarEstadoDeOrden useCase;
  OrdenDeTrabajo orden;

  @BeforeEach
  void setUp() {
    ordenRepo = new OrdenRepositoryFake();
    useCase = new AvanzarEstadoDeOrden(ordenRepo);

    var clienteRepo = new ClienteRepositoryFake();
    var crearClienteUseCase = new RegistrarCliente(clienteRepo);
    var crearClienteInput = new CrearClienteCMD("Luis", "3173045326", "correo@correo.com");
    crearClienteUseCase.ejecutar(crearClienteInput);

    var vehiculoRepo = new VehiculoRepositoryFake();
    var crearVehiculoUseCase = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    var crearVehiculoInput = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "Chevrolet", 2015);
    crearVehiculoUseCase.ejecutar(crearVehiculoInput);

    var empleadoRepo = new EmpleadoRepositoryFake();
    var crearEmpleadoUseCase = new ContratarEmpleado(empleadoRepo);
    var crearEmpleadoInput = new CrearEmpleadoCMD("Alexis", "3208142119", "correo2@correo.com", "Mecanico", "fijo");
    crearEmpleadoUseCase.ejecutar(crearEmpleadoInput);

    var crearOrdenUseCase = new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);
    var crearOrdenInput = new CrearOrdenCMD("CYJ691", "EMP001");
    orden = crearOrdenUseCase.ejecutar(crearOrdenInput).getValue();
  }

  @Nested
  @DisplayName("Creacion Exitosa")
  class CreacionExitosa {

    @Test
    @DisplayName("Si el input es correcto se avanza de estado correctamente")
    void inputCorrecto_AvanzaDeEstado() {
      var input = new AvanzarEstadoDeOrdenCMD(orden.getID());
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(EstadoDelTrabajo.EN_PROCESO, orden.getEstado()));
    }

    @Test
    @DisplayName("Pasa por todos los estados")
    void pasaPorTodosLosEstados() {
      var input = new AvanzarEstadoDeOrdenCMD(orden.getID());
      EstadoDelTrabajo estadoInicial = orden.getEstado();
      var resultado1 = useCase.ejecutar(input); // en proceso
      EstadoDelTrabajo estado1 = resultado1.getValue().getEstado();
      var resultado2 = useCase.ejecutar(input); // en espera de pago
      EstadoDelTrabajo estado2 = resultado2.getValue().getEstado();
      orden.registrarPago(new BigDecimal("18000"));
      var resultado3 = useCase.ejecutar(input); // finalizado
      EstadoDelTrabajo estado3 = resultado3.getValue().getEstado();

      assertAll(
          () -> assertEquals(EstadoDelTrabajo.PENDIENTE, estadoInicial),
          () -> assertTrue(resultado1.isSuccess),
          () -> assertEquals(EstadoDelTrabajo.EN_PROCESO, estado1),
          () -> assertTrue(resultado2.isSuccess),
          () -> assertEquals(EstadoDelTrabajo.EN_ESPERA_DE_PAGO, estado2),
          () -> assertTrue(resultado3.isSuccess),
          () -> assertEquals(EstadoDelTrabajo.FINALIZADO, estado3));
    }

    @Test
    @DisplayName("El estado se actualiza en el repositorio")
    void estadoActualizadoEnElRepositorio() {
      var input = new AvanzarEstadoDeOrdenCMD(orden.getID());
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(orden.getEstado(), ordenRepo.buscarPorId(orden.getID()).get().getEstado()),
          () -> assertEquals(EstadoDelTrabajo.EN_PROCESO, ordenRepo.buscarPorId(orden.getID()).get().getEstado()));
    }
  }

  @Nested
  @DisplayName("Creacion Erronea")
  class CreacionErronea {

    @Test
    @DisplayName("Si el input es null, retorna un error")
    void inputNull_RetornaError() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error los datos no pueden ser nulos", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "ORD002", "arst" })
    @DisplayName("Si la orden no existe o es invalida, retorna error")
    void ordenInvalida_RetornaError(String orden) {
      var input = new AvanzarEstadoDeOrdenCMD(orden);
      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @Test
    @DisplayName("si no se ha ingresado el valor de venta, retorna error")
    void faltaValorVenta_RetornaError() {
      var input = new AvanzarEstadoDeOrdenCMD(orden.getID());
      useCase.ejecutar(input); // en proceso
      useCase.ejecutar(input); // en espera de pago
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("No se puede pasar a finalizado sin definir el pago", resultado.getError().getMessage()));
    }

    @Test
    @DisplayName("Retorna error si ya se ha llegado al estado 'Finalizado' ")
    void ordenFinalizada_RetornaError() {
      var input = new AvanzarEstadoDeOrdenCMD(orden.getID());
      useCase.ejecutar(input); // en proceso
      useCase.ejecutar(input); // en espera de pago
      orden.registrarPago(new BigDecimal("120000"));
      useCase.ejecutar(input); // finalizado
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("La orden ya esta finalizada", resultado.getError().getMessage()));
    }
  }
}
