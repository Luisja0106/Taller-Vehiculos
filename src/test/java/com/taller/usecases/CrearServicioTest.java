package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearServicioCMD;
import com.taller.usecases.fakes.ServicioRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class CrearServicioTest {
  ServicioRepositoryFake repo;
  CrearServicio useCase;

  @BeforeEach
  void setUp() {
    repo = new ServicioRepositoryFake();
    useCase = new CrearServicio(repo);
  }

  @Nested
  @DisplayName("Creacion Exitosa")
  class CreacionExitosa {

    @Test
    @DisplayName("Si el input es correcto, se crea el servicio sin problemas")
    void inputCorrecto_SeCreaElServicioSinProblemas() {

      var serviceInput = new CrearServicioCMD("Cambio de aceite", "150000");

      var result = useCase.ejecutar(serviceInput);

      assertAll(
          () -> assertTrue(result.isSuccess),
          () -> assertEquals("SRV001", result.getValue().getId()));
    }

    @ParameterizedTest
    @ValueSource(strings = { "150000", "15.000", "15,000", "15 000", "15.000,0" })
    @DisplayName("El valor debe poder recibir multiples tipo de datos en String")
    void valorNumerico_seCreaCorrectamente(String valor) {

      var serviceInput = new CrearServicioCMD("Cambio de aceite", valor);

      var result = useCase.ejecutar(serviceInput);

      assertAll(
          () -> assertTrue(result.isSuccess));
    }

    @Test
    @DisplayName("Si existe un servicio 001, el siguiente debe ser 002")
    void asignacionDeIdCorrecta() {
      int cantidadDeServicios = 10;

      for (int i = 1; i < cantidadDeServicios; i++) {
        var input = new CrearServicioCMD("Cambio de aceite" + i, "100000");
        var resultado = useCase.ejecutar(input);

        String idEsperado = String.format("SRV%03d", i);

        assertAll(
            () -> assertTrue(resultado.isSuccess),
            () -> assertEquals(idEsperado, resultado.getValue().getId()));
      }
    }
  }

  @Nested
  @DisplayName("Datos invalidos")
  class DatosInvalidos {

    @Test
    @DisplayName("Si el input es null, retorna error")
    void inputNull_RetornaError() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error los datos no pueden ser nulos", resultado.getError().getMessage()));
    }

    @Test
    @DisplayName("No pueden existir dos servicios con el mismo nombre")
    void dosServiciosMismoNombre_RetornaError() {
      var input = new CrearServicioCMD("Cambio de aceite", "14");

      var resultado = useCase.ejecutar(input);
      var resultado2 = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertFalse(resultado2.isSuccess),
          () -> assertEquals("Error ya existe un Servicio con el nombre ingresado",
              resultado2.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "precioInvalido", "134eeee", "-1", "0" })
    @DisplayName("Retorna error si el valor ingresado es invalido")
    void precioInvalido_RetornaError(String precio) {
      var input = new CrearServicioCMD("Cambio de bujia", precio);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Retorna error si el nombre ingresado es invalido")
    void nombreInvalido_RetornaError(String nombre) {
      var input = new CrearServicioCMD(nombre, "14");

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }
  }
}
