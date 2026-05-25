package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListarEmpleadosTest {

  EmpleadoRepositoryFake repo;
  ListarEmpleados useCase;

  @BeforeEach
  void setUp() {
    repo = new EmpleadoRepositoryFake();
    useCase = new ListarEmpleados(repo);
  }

  @Nested
  @DisplayName("Listado Exitoso")
  class ListadoExitoso {

    @Test
    @DisplayName("Si no hay empleados, debe retornar una lista vacia")
    void sinEmpleados_RetornaListaVacia() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(0, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si hay empleados registrados, debe retornar todos")
    void conEmpleados_RetornaTodos() {
      var contratar = new ContratarEmpleado(repo);
      contratar.ejecutar(new CrearEmpleadoCMD("Luis", "3208142119", "correo1@correo.com", "mecanico", "fijo"));
      contratar.ejecutar(new CrearEmpleadoCMD("Carlos", "3108142119", "correo2@correo.com", "mecanico", "parcial"));
      contratar.ejecutar(new CrearEmpleadoCMD("Ana", "3008142119", "correo3@correo.com", "administrador", "fijo"));

      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(3, resultado.getValue().size()));
    }

    @Test
    @DisplayName("La lista debe contener los empleados correctos")
    void conEmpleados_ContieneEmpleadosCorrectos() {
      var contratar = new ContratarEmpleado(repo);
      contratar.ejecutar(new CrearEmpleadoCMD("Luis", "3208142119", "correo1@correo.com", "mecanico", "fijo"));
      contratar.ejecutar(new CrearEmpleadoCMD("Carlos", "3108142119", "correo2@correo.com", "mecanico", "parcial"));

      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("EMP001", resultado.getValue().get(0).getId()),
          () -> assertEquals("EMP002", resultado.getValue().get(1).getId()));
    }
  }
}
