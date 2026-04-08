package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Empleado;
import com.taller.usecases.dto.ActualizarEmpleadoCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ActualizarEmpleadoTest {
  EmpleadoRepositoryFake repo;
  ActualizarEmpleado useCase;
  Empleado empleado;

  @BeforeEach
  void setUp() {
    repo = new EmpleadoRepositoryFake();
    useCase = new ActualizarEmpleado(repo);

    var empleadoUseCase = new ContratarEmpleado(repo);
    var empleadoInput = new CrearEmpleadoCMD("Luis", "3208142119", "correo@correo.com", "Mecanico", "Fijo");

    empleado = empleadoUseCase.ejecutar(empleadoInput).getValue();

  }

  @Nested
  @DisplayName("Actualizacion Exitosa")
  class ActualizacionExitosa {

    @Test
    @DisplayName("con input correcto, se actualiza exitosamente")
    void inputCorrecto_ActualizacionExitoso() {
      var input = new ActualizarEmpleadoCMD("EMP001", "Luis2", "3215673423", "correo2@correo.com", "Administrador",
          "Parcial");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(input.nombre(), resultado.getValue().getNombre()),
          () -> assertEquals(input.telefono(), resultado.getValue().getTelefono().toString()),
          () -> assertEquals(input.email(), resultado.getValue().getEmail().toString()),
          () -> assertEquals(input.rol().toUpperCase(), resultado.getValue().getRol().toString()),
          () -> assertEquals(input.contrato().toUpperCase(), resultado.getValue().getContrato().toString()));
    }
  }
}
