package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearServicioCMD;
import com.taller.usecases.fakes.ServicioRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
    @ValueSource(strings = { "150000", "15.000", "15,000" })
    @DisplayName("El valor debe poder recibir multiples tipo de datos en String")
    void valorNumerico_seCreaCorrectamente(String valor) {

      var serviceInput = new CrearServicioCMD("Cambio de aceite", valor);

      var result = useCase.ejecutar(serviceInput);

      assertAll(
          () -> assertTrue(result.isSuccess));
    }

  }
}
