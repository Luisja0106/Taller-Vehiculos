package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListarClientesTest {

  ClienteRepositoryFake repo;
  ListarClientes useCase;

  @BeforeEach
  void setUp() {
    repo = new ClienteRepositoryFake();
    useCase = new ListarClientes(repo);
  }

  @Nested
  @DisplayName("Listado Exitoso")
  class ListadoExitoso {

    @Test
    @DisplayName("Si no hay clientes, debe retornar una lista vacia")
    void sinClientes_RetornaListaVacia() {
      var resultado = useCase.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(0, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si hay clientes registrados, debe retornar todos")
    void conClientes_RetornaTodos() {
      var registrar = new RegistrarCliente(repo);
      registrar.ejecutar(new CrearClienteCMD("Luis", "3208142119", "correo1@correo.com"));
      registrar.ejecutar(new CrearClienteCMD("Carlos", "3108142119", "correo2@correo.com"));
      registrar.ejecutar(new CrearClienteCMD("Ana", "3008142119", "correo3@correo.com"));

      var resultado = useCase.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(3, resultado.getValue().size()));
    }

    @Test
    @DisplayName("La lista debe contener los clientes correctos")
    void conClientes_ContieneClientesCorrectos() {
      var registrar = new RegistrarCliente(repo);
      registrar.ejecutar(new CrearClienteCMD("Luis", "3208142119", "correo1@correo.com"));
      registrar.ejecutar(new CrearClienteCMD("Carlos", "3108142119", "correo2@correo.com"));

      var resultado = useCase.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("CLI001", resultado.getValue().get(0).getId()),
          () -> assertEquals("CLI002", resultado.getValue().get(1).getId()));
    }
  }
}
