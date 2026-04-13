package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.valueobjects.Placa;
import com.taller.usecases.dto.ActualizarVehiculoCMD;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;
import com.taller.usecases.fakes.VehiculoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ActualizarVehiculosTest {

  VehiculoRepositoryFake vehiculoRepo;
  ClienteRepositoryFake clienteRepo;
  ActualizarVehiculo useCase;
  Vehiculo vehiculo;

  @BeforeEach
  void setUp() {
    vehiculoRepo = new VehiculoRepositoryFake();
    clienteRepo = new ClienteRepositoryFake();
    useCase = new ActualizarVehiculo(vehiculoRepo, clienteRepo);

    var clienteUseCase = new RegistrarCliente(clienteRepo);
    var crearClienteCMD = new CrearClienteCMD("Luis", "3208142119", "correo@correo.com");
    clienteUseCase.ejecutar(crearClienteCMD);
    var crearVehiculoUseCase = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    var crearVehiculoCMD = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "Chevrolet", 2016);
    vehiculo = crearVehiculoUseCase.ejecutar(crearVehiculoCMD).getValue();
  }

  @Nested
  @DisplayName("Creacion Exitosa")
  class CreacionExitosa {

    @Test
    @DisplayName("si el input es correcto se actualiza sin proplemas")
    void inputCorrecto_ActualizacionExitosa() {
      var input = new ActualizarVehiculoCMD("CYJ691", null, "fortuner", "Mazda", "2015");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(input.modelo(), resultado.getValue().getModelo()),
          () -> assertEquals(input.marca(), resultado.getValue().getMarca()),
          () -> assertEquals(input.año(), String.valueOf(resultado.getValue().getAnio())));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si el modelo es null, actualiza el resto de datos")
    void siElModeloEsNull_ActualizaElRestoIgual(String modelo) {
      var input = new ActualizarVehiculoCMD("CYJ691", null, modelo, "Mazda", "2015");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Clio", resultado.getValue().getModelo()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si la marca es null, actualiza el resto de datos")
    void siLaMarcaEsNull_ActualizaElRestoIgual(String marca) {
      var input = new ActualizarVehiculoCMD("CYJ691", null, "fortuner", marca, "2015");

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("Chevrolet", resultado.getValue().getMarca()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " " })
    @DisplayName("Si el año es null, actualiza el resto de datos")
    void siElAñoEsNull_ActualizaElRestoIgual(String anio) {
      var input = new ActualizarVehiculoCMD("CYJ691", null, "fortuner", "Mazda", anio);

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("2016", String.valueOf(resultado.getValue().getAnio())));
    }

    @Test
    @DisplayName("Si el cliente se cambia, se actualizan los repositorios")
    void cambioDeUsuario_ActualizacionExitosaEnRepos() {
      var clienteUseCase = new RegistrarCliente(clienteRepo);
      var crearClienteCMD = new CrearClienteCMD("Luis2", "3208143119", "correo2@correo.com");
      var resultadoNuevoCliente = clienteUseCase.ejecutar(crearClienteCMD);
      var input = new ActualizarVehiculoCMD("CYJ691", "CLI002", null, null, null);

      var resultado = useCase.ejecutar(input);
      Placa placaVO = Placa.crear("CYJ691").getValue();
      var actualizacionRepoVehiculos = vehiculoRepo.buscarPorPlaca(placaVO);

      var actualizacionVehiculoCliente = clienteRepo.buscarPorId("CLI002").get().getVehiculos().get(0);

      var clienteOriginalNoVehiculos = clienteRepo.buscarPorId("CLI001").get().getVehiculos().isEmpty();
      assertAll(
          () -> assertTrue(resultadoNuevoCliente.isSuccess),
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(input.nuevoDueñoId(), resultado.getValue().getDueño().getId()),
          () -> assertTrue(actualizacionRepoVehiculos.isPresent()),
          () -> assertEquals(actualizacionVehiculoCliente.getPlaca().getValue(), input.placaVehiculo()),
          () -> assertTrue(clienteOriginalNoVehiculos));
    }

  }

  @Nested
  @DisplayName("Creacion Erronea")
  class CreacionErronea {

    @Test
    @DisplayName("Si el input es null, retorna error")
    void inputNull_ReturnaError() {
      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error los datos a cambiar no puden ser nulos", resultado.getError()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "vehiculoInvalido", "arst" })
    @DisplayName("Si el vehiculo es invalido o no existe, retorna error")
    void vehiculoInvalido_RetornaError(String vehiculoPlaca) {
      var input = new ActualizarVehiculoCMD(vehiculoPlaca, null, "modelo2", "Mazda", "2016");

      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);

    }

    @ParameterizedTest
    @ValueSource(strings = { "CLI002", "Cliente invalido", "Cliente no existente" })
    @DisplayName("Si el cliente no existe o es invalido, retorna error")
    void clienteInvalido_RetornaError(String clienteId) {
      var input = new ActualizarVehiculoCMD("CYJ691", clienteId, "modelo2", "Mazda", "2016");

    }
  }

}
