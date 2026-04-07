package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Cliente;
import com.taller.usecases.dto.ActualizarClienteCMD;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ActualizarClienteTest {
  ClienteRepositoryFake repo;
  ActualizarCliente useCase;
  Cliente cliente;

  @BeforeEach
  void setUp() {
    repo = new ClienteRepositoryFake();
    useCase = new ActualizarCliente(repo);

    var cmd = new CrearClienteCMD("Luis", "3208142119", "correo@correo.com");
    var crearClienteUseCase = new RegistrarCliente(repo);
    cliente = crearClienteUseCase.ejecutar(cmd).getValue();
  }

  @Nested
  @DisplayName("Si todo es correcto, Actualizacion Exitosa")
  class ActualizacionExitosa {

    @Test
    @DisplayName("Si el input es correcto, se actualiza sin problemas")
    void inputCorrecto_ActualizacionExitosa() {
      var input = new ActualizarClienteCMD("CLI001", "Luis2", "3218142119", "correo2@correo.com");

      var resultdado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultdado.isSuccess),
          () -> assertEquals(input.nombre(), resultdado.getValue().getNombre()),
          () -> assertEquals(input.email(), resultdado.getValue().getEmail().toString()),
          () -> assertEquals(input.telefono(), resultdado.getValue().getTelefono().toString()));
    }

  }

}
