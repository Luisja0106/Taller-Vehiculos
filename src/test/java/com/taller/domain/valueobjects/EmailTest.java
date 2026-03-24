package com.taller.domain.valueobjects;

import com.taller.domain.utils.Result;
import com.taller.domain.interfaces.IErrorApp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

// No public — JUnit 5 doesn't require it, package-private is cleaner
class EmailTest {

  // @Nested lets you group tests by scenario — exactly like IntelliJ's test
  // groups
  // This makes neotest's summary panel much easier to read
  @Nested
  @DisplayName("cuando el email es válido")
  class CuandoElEmailEsValido {

    @Test
    @DisplayName("debería crear el email correctamente")
    void deberiaCrearElEmailCorrectamente() {
      // ARRANGE — the input
      String emailValido = "juan@gmail.com";

      // ACT — call the method you're testing
      Result<Email, IErrorApp> result = Email.crear(emailValido);

      // ASSERT — verify what happened
      // assertTrue checks that the condition is true, fails the test if not
      assertTrue(result.isSuccess,
          "Se esperaba éxito pero fue error"); // message shown when test fails
    }

    @Test
    @DisplayName("debería normalizar el email a minúsculas")
    void deberiaNormalizarAMinusculas() {
      Result<Email, IErrorApp> result = Email.crear("JUAN@GMAIL.COM");

      assertTrue(result.isSuccess);
      // toString() returns the valor inside Email
      // assertEquals(expected, actual) — note: expected ALWAYS goes first
      assertEquals("juan@gmail.com", result.getValue().toString());
    }

    @Test
    @DisplayName("debería no permitir espacios del email")
    void deberiaEliminarEspacios() {
      Result<Email, IErrorApp> result = Email.crear(" juan@gmail.com");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("dos emails con el mismo valor deberían ser iguales")
    void dosEmailsIgualesDeberianSerIguales() {
      Result<Email, IErrorApp> r1 = Email.crear("juan@gmail.com");
      Result<Email, IErrorApp> r2 = Email.crear("juan@gmail.com");

      // assertEquals on objects uses your equals() method
      // this tests your hashCode/equals implementation directly
      assertEquals(r1.getValue(), r2.getValue());
    }
  }

  @Nested
  @DisplayName("cuando el email es inválido")
  class CuandoElEmailEsInvalido {

    @Test
    @DisplayName("debería fallar si el email es null")
    void deberiaFallarSiEsNull() {
      Result<Email, IErrorApp> result = Email.crear(null);

      // assertFalse is the opposite of assertTrue
      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("debería fallar si el email está vacío")
    void deberiaFallarSiEstaVacio() {
      Result<Email, IErrorApp> result = Email.crear("");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("debería fallar si el email está en blanco")
    void deberiaFallarSiEstaEnBlanco() {
      Result<Email, IErrorApp> result = Email.crear("   ");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("debería fallar si el email no tiene @")
    void deberiaFallarSiNoTieneArroba() {
      Result<Email, IErrorApp> result = Email.crear("juangmail.com");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("debería fallar si el email no tiene dominio")
    void deberiaFallarSiNoTieneDominio() {
      Result<Email, IErrorApp> result = Email.crear("juan@");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("debería retornar un mensaje de error descriptivo")
    void deberiaRetornarMensajeDeError() {
      Result<Email, IErrorApp> result = Email.crear(null);

      assertFalse(result.isSuccess);
      // verify the error message is actually useful
      assertNotNull(result.getError());
    }
  }
}
