package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.dto.ListarVehiculosCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;
import com.taller.usecases.fakes.VehiculoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListarVehiculosTest {

  VehiculoRepositoryFake vehiculoRepo;
  ClienteRepositoryFake clienteRepo;
  ListarVehiculos useCase;

  @BeforeEach
  void setUp() {
    vehiculoRepo = new VehiculoRepositoryFake();
    clienteRepo = new ClienteRepositoryFake();
    useCase = new ListarVehiculos(vehiculoRepo);

    var registrarCliente = new RegistrarCliente(clienteRepo);
    registrarCliente.ejecutar(new CrearClienteCMD("Luis", "3208142119", "correo1@correo.com"));
    registrarCliente.ejecutar(new CrearClienteCMD("Carlos", "3108142119", "correo2@correo.com"));

    var registrarVehiculo = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    registrarVehiculo.ejecutar(new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "Chevrolet", 2017));
    registrarVehiculo.ejecutar(new CrearVehiculoCMD("ABC123", "CLI001", "Spark", "Chevrolet", 2020));
    registrarVehiculo.ejecutar(new CrearVehiculoCMD("XYZ789", "CLI002", "Mazda3", "Mazda", 2019));
  }

  @Nested
  @DisplayName("Listado Exitoso")
  class ListadoExitoso {

    @Test
    @DisplayName("Si el input es nulo, debe retornar todos los vehiculos")
    void inputNulo_RetornaTodos() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(3, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si el clienteId es vacio, debe retornar todos los vehiculos")
    void clienteIdVacio_RetornaTodos() {
      var resultado = useCase.ejecutar(new ListarVehiculosCMD(""));

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(3, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si se filtra por cliente, debe retornar solo sus vehiculos")
    void filtroPorCliente_RetornaSoloSusVehiculos() {
      var resultado = useCase.ejecutar(new ListarVehiculosCMD("CLI001"));

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(2, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si se filtra por cliente con un vehiculo, debe retornar solo ese vehiculo")
    void filtroPorClienteConUnVehiculo_RetornaUno() {
      var resultado = useCase.ejecutar(new ListarVehiculosCMD("CLI002"));

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(1, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si no hay vehiculos, debe retornar una lista vacia")
    void sinVehiculos_RetornaListaVacia() {
      var repoVacio = new VehiculoRepositoryFake();
      var useCaseVacio = new ListarVehiculos(repoVacio);

      var resultado = useCaseVacio.ejecutar(null);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(0, resultado.getValue().size()));
    }
  }
}
