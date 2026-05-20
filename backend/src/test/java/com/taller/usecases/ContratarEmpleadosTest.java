package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ContratarEmpleadosTest {
  @Nested
  @DisplayName("Dados datos validos, deberia crear un empleado correctamente")
  class CreacionCorrecta {
    EmpleadoRepositoryFake repo;
    ContratarEmpleado useCase;

    @BeforeEach
    void setUp() {
      repo = new EmpleadoRepositoryFake();
      useCase = new ContratarEmpleado(repo);
    }

    @Test
    @DisplayName("Con un comando correcto, deberia ejecutar sin ningun problema")
    void conComandoCorrecto_CreaUnEmpleadoCorrectamente() {

      var input = new CrearEmpleadoCMD("Luis", "3208142119", "luisjaperez0106@gmail.com", "MECANICO", "FIJO");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("EMP001", resultado.getValue().getId()));
    }

    @ParameterizedTest
    @ValueSource(strings = { "MECANICO", "mecanico", "Mecanico" })
    @DisplayName("Deberia aceptar el rol sin inportar la forma de la String")
    void rolLowerOrCapitalLetter_ReturnTrue(String rol) {
      var input = new CrearEmpleadoCMD("Luis", "3208142119", "luisjaperez0106@gmail.com", rol, "FIJO");

      var resultado = useCase.ejecutar(input);

      assertTrue(resultado.isSuccess);
    }

    @ParameterizedTest
    @ValueSource(strings = { "FIJO", "fijo", "Fijo" })
    @DisplayName("Deberia aceptar el tipo de contrato sin inportar la forma de la String")
    void tipoDebeRetornarTrueSinInportarElCase(String tipoDeContrato) {
      var input = new CrearEmpleadoCMD("Luis", "3208142119", "luisjaperez0106@gmail.com", "MECANICO", tipoDeContrato);

      var resultado = useCase.ejecutar(input);

      assertTrue(resultado.isSuccess);
    }

    @Test
    @DisplayName("Si exiten un empleado 001, el siguiente deberia ser 002 y asi sucecivamente")
    void asignacionCorrectaDeID() {

      int cantidadDeEmpleado = 10;

      for (int i = 1; i <= cantidadDeEmpleado; i++) {

        String correo = "correo" + i + "@correo.com";
        var input = new CrearEmpleadoCMD("Luis", "3208142119", correo, "MECANICO", "FIJO");
        var resultado = useCase.ejecutar(input);
        String idEsperado = String.format("EMP%03d", i);
        int idFinal = i;

        assertAll(
            () -> assertTrue(resultado.isSuccess),
            () -> assertEquals(idEsperado, resultado.getValue().getId(),
                "El empleado " + idFinal + "deberia tener el id" + idEsperado));
      }
    }
  }

  @Nested
  @DisplayName("Con datos invalidos, deberia arrojar un error")
  class creacionConDatosInvalidos {
    EmpleadoRepositoryFake repo;
    ContratarEmpleado useCase;

    @BeforeEach
    void setUp() {
      repo = new EmpleadoRepositoryFake();
      useCase = new ContratarEmpleado(repo);
    }

    @Test
    @DisplayName("Si el input es nulo, deberia retonar un error")
    void inputNulo_RetornaError() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Los datos no pueden ser nulos", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si el nombre es invalido, deberia ejecutar un error")
    void nombreInvalido_RetornaError(String nombre) {
      var input = new CrearEmpleadoCMD(nombre, "3208142119", "correo@correo.com", "MECANICO", "FIJO");
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error nombre invalido", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "telefonoinvalido", "123", "00000000000000000000000" })
    @DisplayName("Si el telefono es invalido, deberia ejecutar un error")
    void telefonoInvalido_RetornaError(String telefono) {

      var input = new CrearEmpleadoCMD("luis", telefono, "correo@correo.com", "MECANICO", "FIJO");

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "13423", "correosinarroba", "correo@", "@dominio" })
    @DisplayName("Si el email es invalido, deberia ejecutar un error")
    void emailInvalido_RetornaError(String email) {
      var input = new CrearEmpleadoCMD("Luis", "3208142119", email, "MECANICO", "FIJO");

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "rol invalido" })
    @DisplayName("Si el rol es invalido, deberia ejecutar un error")
    void rolInvalido_RetornaError(String rol) {
      var input = new CrearEmpleadoCMD("Luis", "3208142119", "email@correo.com", rol, "FIJO");

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "rol invalido" })
    @DisplayName("Si el tipo de contrato es invalido, deberia ejecutar un error")
    void tipoInvalido_retornaError(String tipo) {
      var input = new CrearEmpleadoCMD("Luis", "3208142119", "email@correo.com", "MECANICO", tipo);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);

    }

    @Test
    @DisplayName("Si el email es repetido debe retornar un error")
    void emailRepetido_RetornaError() {
      String email = "correo@correo.com";

      var input1 = new CrearEmpleadoCMD("luis", "3208142119", email, "MECANICO", "FIJO");
      var input2 = new CrearEmpleadoCMD("luis2", "3200142119", email, "MECANICO", "FIJO");

      var resultado1 = useCase.ejecutar(input1);

      var resultado2 = useCase.ejecutar(input2);

      assertAll(
          () -> assertTrue(resultado1.isSuccess),
          () -> assertFalse(resultado2.isSuccess),
          () -> assertEquals("Error email ya registrado", resultado2.getError().getMessage()));

    }
  }

}
