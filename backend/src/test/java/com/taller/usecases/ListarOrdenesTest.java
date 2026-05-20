package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.dto.ListarOrdenesCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;
import com.taller.usecases.fakes.OrdenRepositoryFake;
import com.taller.usecases.fakes.VehiculoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListarOrdenesTest {

  OrdenRepositoryFake ordenRepo;
  VehiculoRepositoryFake vehiculoRepo;
  EmpleadoRepositoryFake empleadoRepo;
  ListarOrdenes useCase;

  @BeforeEach
  void setUp() {
    ordenRepo = new OrdenRepositoryFake();
    vehiculoRepo = new VehiculoRepositoryFake();
    empleadoRepo = new EmpleadoRepositoryFake();
    useCase = new ListarOrdenes(ordenRepo);

    var clienteRepo = new ClienteRepositoryFake();
    var registrarCliente = new RegistrarCliente(clienteRepo);
    registrarCliente.ejecutar(new CrearClienteCMD("Luis", "3208142119", "correo1@correo.com"));
    registrarCliente.ejecutar(new CrearClienteCMD("Carlos", "3108142119", "correo2@correo.com"));

    var registrarVehiculo = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    registrarVehiculo.ejecutar(new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "Chevrolet", 2017));
    registrarVehiculo.ejecutar(new CrearVehiculoCMD("ABC123", "CLI002", "Spark", "Chevrolet", 2020));

    var contratarEmpleado = new ContratarEmpleado(empleadoRepo);
    contratarEmpleado.ejecutar(new CrearEmpleadoCMD("Alexis", "3208142119", "emp1@correo.com", "mecanico", "fijo"));
    contratarEmpleado.ejecutar(new CrearEmpleadoCMD("Pedro", "3108142119", "emp2@correo.com", "mecanico", "parcial"));

    var crearOrden = new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);
    crearOrden.ejecutar(new CrearOrdenCMD("CYJ691", "EMP001"));
    crearOrden.ejecutar(new CrearOrdenCMD("ABC123", "EMP002"));
  }

  @Nested
  @DisplayName("Listado Exitoso")
  class ListadoExitoso {

    @Test
    @DisplayName("Si el input es nulo, debe retornar todas las ordenes")
    void inputNulo_RetornaTodas() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(2, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si se filtra por estado pendiente, debe retornar las ordenes con ese estado")
    void filtroPorEstado_RetornaOrdenesConEseEstado() {
      var resultado = useCase.ejecutar(new ListarOrdenesCMD("Pendiente", null, null));

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(2, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si se filtra por empleado, debe retornar solo sus ordenes")
    void filtroPorEmpleado_RetornaSusOrdenes() {
      var resultado = useCase.ejecutar(new ListarOrdenesCMD(null, null, "EMP001"));

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(1, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si se filtra por placa de vehiculo, debe retornar solo sus ordenes")
    void filtroPorPlaca_RetornaSusOrdenes() {
      var resultado = useCase.ejecutar(new ListarOrdenesCMD(null, "CYJ691", null));

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(1, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si no hay ordenes, debe retornar una lista vacia")
    void sinOrdenes_RetornaListaVacia() {
      var repoVacio = new OrdenRepositoryFake();
      var useCaseVacio = new ListarOrdenes(repoVacio);

      var resultado = useCaseVacio.ejecutar(null);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(0, resultado.getValue().size()));
    }
  }

  @Nested
  @DisplayName("Datos invalidos")
  class DatosInvalidos {

    @Test
    @DisplayName("Si el estado es invalido, debe retornar error")
    void estadoInvalido_RetornaError() {
      var resultado = useCase.ejecutar(new ListarOrdenesCMD("EstadoInexistente", null, null));

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error estado invalido", resultado.getError().getMessage()));
    }
  }
}
