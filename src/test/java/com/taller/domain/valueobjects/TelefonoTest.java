package com.taller.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.*;

import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TelefonoTest {

  @Nested
  @DisplayName("cuando el telefono es valido")
  class CuandoElTelefonoEsValido {

    @Test
    @DisplayName("deberia crear el telefono correctamente con +")
    void deberiaCrearTelefonoConMas() {
      Result<Telefono, IErrorApp> result = Telefono.crear("+57320812119");

      assertTrue(result.isSuccess, "Se esperaba un exito pero fue un error");
    }

    @Test
    @DisplayName("deberia crear el telefono")
    void deberiaCrearTelefono() {
      Result<Telefono, IErrorApp> result = Telefono.crear("320812119");

      assertTrue(result.isSuccess, "Se esperaba un exito pero fue un error");
    }

    @Test
    @DisplayName("dos telefonos deberian ser iguales")
    void dosTelefonosDeberianSerIguales() {
      Result<Telefono, IErrorApp> r1 = Telefono.crear("320812119");
      Result<Telefono, IErrorApp> r2 = Telefono.crear("320812119");

      assertEquals(r1.getValue(), r2.getValue());
    }

  }

  @Nested
  @DisplayName("cuando el telefono es invalido")
  class CuandoElTelefonoEsInvalido {

    @Test
    @DisplayName("deberia fallar si es null")
    void deberiaFallarSiEsnull() {
      Result<Telefono, IErrorApp> result = Telefono.crear(null);

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia fallar si esta vacio")
    void deberiaFallarSiEsVacio() {
      Result<Telefono, IErrorApp> result = Telefono.crear("");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia fallar si no tiene numeros")
    void deberiaFallarSiNoTieneNumeros() {
      Result<Telefono, IErrorApp> result = Telefono.crear("argrrtnen");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia fallar si esta en blanco")
    void deberiaFallarSiEsBlanco() {
      Result<Telefono, IErrorApp> result = Telefono.crear(" ");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia retornar un mensaje descriptivo")
    void deberiaRetornarUnMensaje() {
      Result<Telefono, IErrorApp> result = Telefono.crear(null);

      assertFalse(result.isSuccess);

      assertNotNull(result.getError());
    }
  }
}
