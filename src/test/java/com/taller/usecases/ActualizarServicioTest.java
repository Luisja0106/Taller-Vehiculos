package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import com.taller.domain.entities.Servicio;
import com.taller.usecases.dto.ActualizarServicioCMD;
import com.taller.usecases.dto.CrearServicioCMD;
import com.taller.usecases.fakes.ServicioRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ActualizarServicioTest {
  ServicioRepositoryFake repo;
  ActualizarServicio useCase;
  Servicio servicio;

  @BeforeEach
  void setUp() {
    repo = new ServicioRepositoryFake();
    useCase = new ActualizarServicio(repo);

    var crearServicioCMD = new CrearServicioCMD("Servicio", "15");

    var crearServicioUseCase = new CrearServicio(repo);

    servicio = crearServicioUseCase.ejecutar(crearServicioCMD).getValue();
  }

  @Nested
  @DisplayName("Creacion Exitosa")
  class CreacionExitosa {

    @Test
    @DisplayName("Si el input es correcto, Actualiza sin error")
    void inputCorrecto_ActualizacionCorrecta() {
      var input = new ActualizarServicioCMD("SRV001", "Servicio nuevo", "16");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(input.nombre(), servicio.getNombreDelServicio()),
          () -> assertEquals(new BigDecimal(input.precio()), servicio.getPrecio()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "" })
    @DisplayName("Si el nombre no se digita se Actualiza igualmente")
    void nombreNull_ActualizacionExitosa(String nombre) {
      var input = new ActualizarServicioCMD("SRV001", nombre, "16");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Servicio", servicio.getNombreDelServicio()),
          () -> assertEquals(new BigDecimal(input.precio()), servicio.getPrecio()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "" })
    @DisplayName("Si el Precio no se digita se Actualiza igualmente")
    void precioNull_ActualizacionExitosa(String precio) {
      var input = new ActualizarServicioCMD("SRV001", "servicio nuevo", precio);

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(input.nombre(), servicio.getNombreDelServicio()),
          () -> assertEquals(new BigDecimal("15"), servicio.getPrecio()));
    }

    @Test
    @DisplayName("Se actualiza correctamente en el repositorio")
    void actualizacionCorrectaEnElRepo() {

      var input = new ActualizarServicioCMD("SRV001", "Servicio nuevo", "16");

      var resultado = useCase.ejecutar(input);

      Servicio servicioInRepo = repo.buscarPorId(input.servicioId()).get();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(servicio, servicioInRepo),
          () -> assertEquals(input.nombre(), servicioInRepo.getNombreDelServicio()),
          () -> assertEquals(new BigDecimal(input.precio()), servicioInRepo.getPrecio()));
    }
  }
}
