package com.taller.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.*;

import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlacaTest {

  @Nested
  @DisplayName("Cuando es valido")
  class CuandoEsValido {
    @Test
    @DisplayName("deberi crear la placa correctamente")
    void deberiaCrearLaPlacaCorrectamente() {
      Result<Placa, IErrorApp> result = Placa.crear("CYJ651");

      assertTrue(result.isSuccess, "Se esperaba exito pero fue error");
    }

    @Test
    @DisplayName("deberia normalizar todo a mayus")
    void deberiaNormalizarAMayus() {
      Result<Placa, IErrorApp> result = Placa.crear("cyj651");

      assertTrue(result.isSuccess);
    }

    @Test
    @DisplayName("deberia no permitir espacio")
    void deberiaNoPermitirEspacios() {
      Result<Placa, IErrorApp> result = Placa.crear(" cyj651");

      assertTrue(result.isSuccess);
      assertEquals("CYJ651", result.getValue().toString());
    }

    @Test
    @DisplayName("deberian ser iguales")
    void deberianSerIguales() {
      Result<Placa, IErrorApp> r1 = Placa.crear("cyj651");
      Result<Placa, IErrorApp> r2 = Placa.crear("cyj651");

      assertEquals(r1.getValue(), r2.getValue());
    }
  }

  @Nested
  @DisplayName("cuando la placa es invalida")
  class CuandoLaPlacaEsInvalida {

    @Test
    @DisplayName("deberia fallar si es null")
    void deberiaFallarSiEsNull() {
      Result<Placa, IErrorApp> result = Placa.crear(null);

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia Fallar si es vacio")
    void deberiaFallarSiEsVacio() {
      Result<Placa, IErrorApp> result = Placa.crear("");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia Fallar si esta en blanco")
    void deberiaFallarSiEstaEnBlanco() {
      Result<Placa, IErrorApp> result = Placa.crear("  ");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia fallar si no tiene numeros")
    void deberiaFallarSiNoTieneNumeros() {
      Result<Placa, IErrorApp> result = Placa.crear("CYJGWR");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia fallar si no tiene letras")
    void deberiaFallarSiNoTieneLetras() {
      Result<Placa, IErrorApp> result = Placa.crear("123456");

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("deberia retonar un mensaje de error descriptivo")
    void deberiaRetornarUnMsjDeError() {
      Result<Placa, IErrorApp> result = Placa.crear("null");

      assertFalse(result.isSuccess);

      assertNotNull(result.getError());
    }
  }

}
