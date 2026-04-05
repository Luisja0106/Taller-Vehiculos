package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class RegistrarClienteTest {

  @Nested
  @DisplayName("Creacion exitosa")
  class CreacionExitosa {

    ClienteRepositoryFake repo;
    RegistrarCliente useCase;

    @BeforeEach
    void setUp() {
      repo = new ClienteRepositoryFake();
      useCase = new RegistrarCliente(repo);
    }

    @Test
    @DisplayName("Con un comando correcto, se deberia registrar un cliente sin problemas")
    void comandoCorrecto_CreaClienteSinProblemas() {
      var input = new CrearClienteCMD("luis", "3208142119", "correo@correo.com");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("CLI001", resultado.getValue().getId()));
    }

    @Test
    @DisplayName("Si existe un cliente 001, el siguiente debe ser 002 y asi sucesivamente")
    void asignacionCorrectaDeID() {
      int cantidadDeClientes = 10;

      for (int i = 1; i <= cantidadDeClientes; i++) {
        String correo = "correo" + i + "@correo.com";
        var input = new CrearClienteCMD("Luis", "3208142119", correo);
        var resultado = useCase.ejecutar(input);
        String idEsperado = String.format("CLI%03d", i);
        int idFinal = i;

        assertAll(
            () -> assertTrue(resultado.isSuccess),
            () -> assertEquals(idEsperado, resultado.getValue().getId(),
                "El Cliente " + idFinal + "deberia tener el id " + idEsperado));
      }
    }
  }

  @Nested
  @DisplayName("Con datos invalidos, deberia arrojar un error")
  class creacionConDatosInvalidos {
    ClienteRepositoryFake repo;
    RegistrarCliente useCase;

    @BeforeEach
    void setUp() {
      repo = new ClienteRepositoryFake();
      useCase = new RegistrarCliente(repo);
    }

    @Test
    @DisplayName("Si el input es nulo, deberia retornar un error")
    void inputNulo_retornaError() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Los datos no pueden ser nulos", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "" })
    @DisplayName("Si el nombre es invalido, deberia ejecutar un error")
    void nombreInvalido_RetornaError(String nombre) {
      var input = new CrearClienteCMD(nombre, "3208142119", "correo@correo.com");
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error el nombre es invalido", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "telefono invalido", "123", "00000000000000000000000" })
    @DisplayName("Si el telefono es invalido, deberia retornar error")
    void telefonoInvalido_RetornaError(String telefono) {

      var input = new CrearClienteCMD("luis", telefono, "correo@correo.com");

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "correosinarroba", "correo@", "@dominio" })
    @DisplayName("Si el email es invalido, deberia retornar error")
    void emailInvalido_RetornaError(String email) {

      var input = new CrearClienteCMD("luis", "3208142119", email);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @Test
    @DisplayName("Si el email es repetido debe retornar un error")
    void emailRepetido_RetornaError() {
      String email = "correo@correo.com";

      var input1 = new CrearClienteCMD("luis", "3208142119", email);
      var input2 = new CrearClienteCMD("luis2", "3200142119", email);

      var resultado1 = useCase.ejecutar(input1);

      var resultado2 = useCase.ejecutar(input2);

      assertAll(
          () -> assertTrue(resultado1.isSuccess),
          () -> assertFalse(resultado2.isSuccess),
          () -> assertEquals("El email ya esta registrado", resultado2.getError().getMessage()));

    }
  }
}
