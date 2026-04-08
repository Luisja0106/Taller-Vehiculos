package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Cliente;
import com.taller.usecases.dto.ActualizarClienteCMD;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si solo se actualiza el nombre, el resto permance igual")
    void soloNombre_SoloActualizaElNombre(String variable) {

      var input = new ActualizarClienteCMD("CLI001", "Luis2", variable, variable);

      var resultdado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultdado.isSuccess),
          () -> assertEquals("Luis2", resultdado.getValue().getNombre()),
          () -> assertEquals("3208142119", resultdado.getValue().getTelefono().getValue()),
          () -> assertEquals("correo@correo.com", resultdado.getValue().getEmail().toString()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si solo se actualiza el email, el resto permance igual")
    void soloEmail_SoloActualizaElEmail(String variable) {

      var input = new ActualizarClienteCMD("CLI001", variable, variable, "correo@correo2.com");

      var resultdado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultdado.isSuccess),
          () -> assertEquals("Luis", resultdado.getValue().getNombre()),
          () -> assertEquals("3208142119", resultdado.getValue().getTelefono().getValue()),
          () -> assertEquals("correo@correo2.com", resultdado.getValue().getEmail().toString()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si solo se actualiza el telefono, el resto permance igual")
    void soloTelefono_SoloActualizaElTelefono(String variable) {

      var input = new ActualizarClienteCMD("CLI001", variable, "3208242119", variable);

      var resultdado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultdado.isSuccess),
          () -> assertEquals("Luis", resultdado.getValue().getNombre()),
          () -> assertEquals("3208242119", resultdado.getValue().getTelefono().getValue()),
          () -> assertEquals("correo@correo.com", resultdado.getValue().getEmail().toString()));
    }

    @Test
    @DisplayName("Una vez cambiado, el repositorio debe guardar los cambios")
    void cambiosEnElRepositorio() {
      var input = new ActualizarClienteCMD("CLI001", "Alexis", "3102790845", "correo2@correo.com");

      var resultado = useCase.ejecutar(input);

      var cliente = repo.buscarPorId("CLI001").get();
      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Alexis", cliente.getNombre()),
          () -> assertEquals("3102790845", cliente.getTelefono().getValue()),
          () -> assertEquals("correo2@correo.com", cliente.getEmail().toString()));
    }

  }

  @Nested
  @DisplayName("Creacion erronea")
  class CreacionErronea {

    @Test
    @DisplayName("Si el input es nulo, retorna error")
    void inputNul_RetornaError() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error los datos a actualizar no pueden ser nulos", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "clienteInvalido", "CLI002" })
    @DisplayName("si el cliente no existe o es invalido, retorna error")
    void clienteInvalido_RetornaError(String clienteId) {
      var input = new ActualizarClienteCMD(clienteId, "Luis2", null, null);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

  }

}
