package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Empleado;
import com.taller.usecases.dto.ActualizarEmpleadoCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si solo se cambia el nombre, el resto se mantiene igual")
    void cambiarNombre_RestoIgual(String variable) {
      var input = new ActualizarEmpleadoCMD("EMP001", "Luis2", variable, variable, variable, variable);

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Luis2", resultado.getValue().getNombre()),
          () -> assertEquals("3208142119", resultado.getValue().getTelefono().getValue()),
          () -> assertEquals("correo@correo.com", resultado.getValue().getEmail().toString()),
          () -> assertEquals("Mecanico".toUpperCase(), resultado.getValue().getRol().toString()),
          () -> assertEquals("Fijo".toUpperCase(), resultado.getValue().getContrato().toString()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si solo se cambia el telefono, el resto se mantiene igual")
    void cambiarTelefono_RestoIgual(String variable) {
      var input = new ActualizarEmpleadoCMD("EMP001", variable, "3149245836", variable, variable, variable);

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Luis", resultado.getValue().getNombre()),
          () -> assertEquals("3149245836", resultado.getValue().getTelefono().getValue()),
          () -> assertEquals("correo@correo.com", resultado.getValue().getEmail().toString()),
          () -> assertEquals("Mecanico".toUpperCase(), resultado.getValue().getRol().toString()),
          () -> assertEquals("Fijo".toUpperCase(), resultado.getValue().getContrato().toString()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si solo se cambia el email, el resto se mantiene igual")
    void cambiarEmail_RestoIgual(String variable) {
      var input = new ActualizarEmpleadoCMD("EMP001", variable, variable, "correo2@correo.com", variable, variable);

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Luis", resultado.getValue().getNombre()),
          () -> assertEquals("3208142119", resultado.getValue().getTelefono().getValue()),
          () -> assertEquals("correo2@correo.com", resultado.getValue().getEmail().toString()),
          () -> assertEquals("Mecanico".toUpperCase(), resultado.getValue().getRol().toString()),
          () -> assertEquals("Fijo".toUpperCase(), resultado.getValue().getContrato().toString()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si solo se cambia el rol, el resto se mantiene igual")
    void cambiarRol_RestoIgual(String variable) {
      var input = new ActualizarEmpleadoCMD("EMP001", variable, variable, variable, "Administrador", variable);

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Luis", resultado.getValue().getNombre()),
          () -> assertEquals("3208142119", resultado.getValue().getTelefono().getValue()),
          () -> assertEquals("correo@correo.com", resultado.getValue().getEmail().toString()),
          () -> assertEquals("Administrador".toUpperCase(), resultado.getValue().getRol().toString()),
          () -> assertEquals("Fijo".toUpperCase(), resultado.getValue().getContrato().toString()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si solo se cambia el tipo de contrato, el resto se mantiene igual")
    void cambiarTipo_RestoIgual(String variable) {
      var input = new ActualizarEmpleadoCMD("EMP001", variable, variable, variable, variable, "Parcial");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Luis", resultado.getValue().getNombre()),
          () -> assertEquals("3208142119", resultado.getValue().getTelefono().getValue()),
          () -> assertEquals("correo@correo.com", resultado.getValue().getEmail().toString()),
          () -> assertEquals("Mecanico".toUpperCase(), resultado.getValue().getRol().toString()),
          () -> assertEquals("Parcial".toUpperCase(), resultado.getValue().getContrato().toString()));
    }

    @Test
    @DisplayName("Una vez cambiado el empleado, se actualiza en el repositorio")
    void cambio_RepositoryActualizado() {
      var input = new ActualizarEmpleadoCMD("EMP001", "Luis2", "3215673423", "correo2@correo.com", "Administrador",
          "Parcial");

      var resultado = useCase.ejecutar(input);

      Empleado empleado = repo.buscarPorId("EMP001").get();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Luis2", empleado.getNombre()),
          () -> assertEquals("3215673423", empleado.getTelefono().getValue()),
          () -> assertEquals("correo2@correo.com", empleado.getEmail()),
          () -> assertEquals("Administrador".toUpperCase(), empleado.getRol().toString()),
          () -> assertEquals("Parcial".toUpperCase(), empleado.getContrato().toString()));
    }
  }

  @Nested
  @DisplayName("Creacion incorrecta")
  class CreacionIncorrecta {

    @Test
    @DisplayName("Si el input es null, retorna error")
    void inputNull_RetornaError() {

      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error los datos no pueden ser nulos", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "Empleado invalido", "1234", "EMP002" })
    @DisplayName("si el empleado es invalido, retorna error")
    void empleadoInvalido_RetornaError(String empleadoId) {
      var input = new ActualizarEmpleadoCMD(empleadoId, "Luis2", null, null, null, null);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);

    }

    @Test
    @DisplayName("Si el email es repetido, retorna error")
    void emailRepetido_RetornaError() {
      var empleado2Input = new CrearEmpleadoCMD("Luis", "3208142119", "correo2@correo.com", "Mecanico", "Fijo");
      var crearEmpleadoUseCase = new ContratarEmpleado(repo);

      crearEmpleadoUseCase.ejecutar(empleado2Input);

      var input = new ActualizarEmpleadoCMD("EMP001", null, null, "correo2@correo.com", null, null);

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error el Email ingresado ya se encuentra registrado", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = { "email invalido", "email@", "@dominio", "com", "1234", "null" })
    @DisplayName("Si el email es invalido, retorna Error")
    void emailInvalido_RetornaError(String email) {
      var input = new ActualizarEmpleadoCMD("EMP001", null, null, email, null, null);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @ValueSource(strings = { "telefono invalido", "1234", "3499999999999999999", "3234" })
    @DisplayName("Si el telefono es invalido, retorna Error")
    void telefonoInvalido_RetornaError(String telefono) {
      var input = new ActualizarEmpleadoCMD("EMP001", null, telefono, null, null, null);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @ValueSource(strings = { "rol invalido", "1234", "nriesnteirn", "3234" })
    @DisplayName("Si el rol es invalido, retorna Error")
    void rolInvalido_RetornaError(String rol) {
      var input = new ActualizarEmpleadoCMD("EMP001", null, null, null, rol, null);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @ValueSource(strings = { "contrato invalido", "1234", "nriesnteirn", "3234" })
    @DisplayName("Si el contrato es invalido, retorna Error")
    void contratoInvalido_RetornaError(String contrato) {
      var input = new ActualizarEmpleadoCMD("EMP001", null, null, null, null, contrato);

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

  }
}
