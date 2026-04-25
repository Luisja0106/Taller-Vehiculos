package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.usecases.dto.CrearServicioCMD;
import com.taller.usecases.fakes.ServicioRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListarServiciosTest {

  ServicioRepositoryFake repo;
  ListarServicios useCase;

  @BeforeEach
  void setUp() {
    repo = new ServicioRepositoryFake();
    useCase = new ListarServicios(repo);
  }

  @Nested
  @DisplayName("Listado Exitoso")
  class ListadoExitoso {

    @Test
    @DisplayName("Si no hay servicios, debe retornar una lista vacia")
    void sinServicios_RetornaListaVacia() {
      var resultado = useCase.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(0, resultado.getValue().size()));
    }

    @Test
    @DisplayName("Si hay servicios registrados, debe retornar todos")
    void conServicios_RetornaTodos() {
      var crear = new CrearServicio(repo);
      crear.ejecutar(new CrearServicioCMD("Cambio de aceite", "150000"));
      crear.ejecutar(new CrearServicioCMD("Cambio de bujia", "200000"));
      crear.ejecutar(new CrearServicioCMD("Alineacion", "100000"));

      var resultado = useCase.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(3, resultado.getValue().size()));
    }

    @Test
    @DisplayName("La lista debe contener los servicios correctos")
    void conServicios_ContieneServiciosCorrectos() {
      var crear = new CrearServicio(repo);
      crear.ejecutar(new CrearServicioCMD("Cambio de aceite", "150000"));
      crear.ejecutar(new CrearServicioCMD("Cambio de bujia", "200000"));

      var resultado = useCase.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("SRV001", resultado.getValue().get(0).getId()),
          () -> assertEquals("SRV002", resultado.getValue().get(1).getId()));
    }
  }
}
