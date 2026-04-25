package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Empleado;
import com.taller.domain.entities.Vehiculo;
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

class CrearOrdenDeTrabajoTest {

  OrdenRepositoryFake ordenRepo;
  VehiculoRepositoryFake vehiculoRepo;
  EmpleadoRepositoryFake empleadoRepo;
  ClienteRepositoryFake clienteRepo;
  CrearOrden useCase;

  Empleado empleadoACargo;
  Vehiculo vehiculo;

  @BeforeEach
  void setUp() {
    ordenRepo = new OrdenRepositoryFake();
    vehiculoRepo = new VehiculoRepositoryFake();
    empleadoRepo = new EmpleadoRepositoryFake();

    useCase = new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);

    // objects setup

    var empleadoUseCase = new ContratarEmpleado(empleadoRepo);
    var empleadoInput = new CrearEmpleadoCMD("Luis", "3208142119", "correo@correo.com", "mecanico", "Fijo");
    empleadoACargo = empleadoUseCase.ejecutar(empleadoInput).getValue();

    clienteRepo = new ClienteRepositoryFake();
    var clienteUseCase = new RegistrarCliente(clienteRepo);
    var clienteInput = new CrearClienteCMD("Luis", "3208242119", "correo2@correo.com");
    clienteUseCase.ejecutar(clienteInput);

    var vehiculoUseCase = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    var vehiculoInput = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "Chevrolet", 2017);
    vehiculo = vehiculoUseCase.ejecutar(vehiculoInput).getValue();
  }

  @Nested
  @DisplayName("Creacion Exitosa")
  class CreacionExitosa {

    @Test
    @DisplayName("Si el input es correcto, se debe crear la orden")
    void siElInputEsCorrecto_CreaLaOrden() {
      var ordenInput = new CrearOrdenCMD("CYJ691", "EMP001");

      var resultado = useCase.ejecutar(ordenInput);

      String id = resultado.getValue().getID();
      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertTrue(id.startsWith("ORD")),
          () -> assertTrue(id.endsWith("001")),
          () -> assertEquals(14, id.length()) // ORD = 3, yyyyMMdd = 8, final = 3, total = 14
      );
    }

    @Test
    @DisplayName("El final del ID de las ordenes debe aumentar")
    void elFinalDelIdDebeAumentar() {
      int numeroOrdenes = 10;

      var ordenInput = new CrearOrdenCMD("CYJ691", "EMP001");

      for (int i = 1; i <= numeroOrdenes; i++) {
        var resultado = useCase.ejecutar(ordenInput);
        String idFinalEsperado = String.format("%03d", i);

        assertAll(
            () -> assertTrue(resultado.isSuccess),
            () -> assertTrue(resultado.getValue().getID().endsWith(idFinalEsperado)));
      }
    }

    @Test
    @DisplayName("La orden debe estar en el repositorio despues de ser creada")
    void ordenCreada_apareceEnElRepositorio() {
      var ordenInput = new CrearOrdenCMD("CYJ691", "EMP001");

      var resultado = useCase.ejecutar(ordenInput);
      var existe = ordenRepo.buscarPorId(resultado.getValue().getID());
      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertTrue(existe.isPresent()));
    }

  }

  @Nested
  @DisplayName("Con Datos invalidos debe retornar error")
  class DatosErroneos_RetornaError {

    @Test
    @DisplayName("Si el input es nulo, retorna error")
    void inputNulo_RetornaError() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error los datos no pueden ser nulos", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "placainvalida", "arstasrt", "1234", "1234ersnt" })
    @DisplayName("Si la placa es invalida, retorna un error")
    void placaInvalida_RetornaError(String placa) {
      var input = new CrearOrdenCMD(placa, "EMP001");

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "idInvalido", "arstasrt", "1234", "EMP002" })
    @DisplayName("Si el id de empleado es invalido, retorna un error")
    void idEmpleadoInvalido_RetornaError(String idEmpleado) {
      var input = new CrearOrdenCMD("CYJ691", idEmpleado);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

  }
}
