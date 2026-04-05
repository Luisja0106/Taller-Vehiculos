package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.taller.domain.entities.Cliente;
import com.taller.domain.valueobjects.Placa;
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

class RegistrarVehiculoTest {

  @Nested
  @DisplayName("Creacion exitosa")
  class CreacionExitosa {
    VehiculoRepositoryFake repoVehiculos;
    ClienteRepositoryFake repoClientes;
    RegistrarVehiculo useCase;
    Cliente cliente;

    @BeforeEach
    void setUp() {
      repoVehiculos = new VehiculoRepositoryFake();
      repoClientes = new ClienteRepositoryFake();
      useCase = new RegistrarVehiculo(repoVehiculos, repoClientes);

      var clienteUseCase = new RegistrarCliente(repoClientes);

      var input = new CrearClienteCMD("Luis", "3208142119", "correo@correo.com");

      cliente = clienteUseCase.ejecutar(input).getValue();
    }

    @Test
    @DisplayName("Si el input es correcto, registra un vehiculo sin problemas")
    void inputCorrecto_RegistraSinProblemas() {
      var input = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "CHEVROLET", 2017);

      var resultado = useCase.ejecutar(input);

      assertTrue(resultado.isSuccess);
    }

    @ParameterizedTest
    @ValueSource(strings = { "chevrolet", "CHEVROLET", "Chevrolet" })
    @DisplayName("Marca deberia ser valida en cualquier tipo")
    void marcaEnCualquierCase_RegistraSinProblemas(String marca) {
      var input = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", marca, 2017);

      var resultado = useCase.ejecutar(input);

      assertTrue(resultado.isSuccess);
    }

    @Test
    @DisplayName("El cliente debe tener ese vehiculo")
    void clienteDebeContenerEsevehiculo() {
      var input = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "CHEVROLET", 2017);

      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertFalse(cliente.getVehiculos().isEmpty()),
          () -> assertEquals(cliente.getVehiculos().get(0), resultado.getValue()));
    }

    @Test
    @DisplayName("El vehiculo debe poder encontrarse en el repositorio tras registrarse")
    void vehiculoRegistrado_SePuedeEncontrarEnElRepositorio() {

      var input = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "CHEVROLET", 2017);
      useCase.ejecutar(input);

      var placaVO = Placa.crear("CYJ691").getValue();
      var encontrado = repoVehiculos.buscarPorPlaca(placaVO);

      assertTrue(encontrado.isPresent());
    }
  }

  @Nested
  @DisplayName("Con datos invalidos, deberia retornar error")
  class datosInvalidos_RetornaError {

    VehiculoRepositoryFake repoVehiculos;
    ClienteRepositoryFake repoClientes;
    RegistrarVehiculo useCase;
    Cliente cliente;

    @BeforeEach
    void setUp() {
      repoVehiculos = new VehiculoRepositoryFake();
      repoClientes = new ClienteRepositoryFake();
      useCase = new RegistrarVehiculo(repoVehiculos, repoClientes);

      var clienteUseCase = new RegistrarCliente(repoClientes);

      var input = new CrearClienteCMD("Luis", "3208142119", "correo@correo.com");

      cliente = clienteUseCase.ejecutar(input).getValue();
    }

    @Test
    @DisplayName("si el input es nulo, debe retornar error")
    void inputNulo_RetornaError() {

      var result = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(result.isSuccess),
          () -> assertEquals("Los datos no pueden ser nulos", result.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "PlacaInvalida", "1234342" })
    @DisplayName("Si la placa no es valida retorna error")
    void placaInvalida_RetornaError(String placa) {
      var input = new CrearVehiculoCMD(placa, "CLI001", "Clio", "chevrolet", 2017);

      var result = useCase.ejecutar(input);

      assertFalse(result.isSuccess);
    }

    @Test
    @DisplayName("Si es placa repetida, retorna error")
    void placaRepetida_retornaError() {
      var input = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "chevrolet", 2017);

      var result1 = useCase.ejecutar(input);
      var result2 = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(result1.isSuccess),
          () -> assertFalse(result2.isSuccess),
          () -> assertEquals("Placa ya registrada", result2.getError().getMessage()));

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "marcainvalida", "1234" })
    @DisplayName("Si la marca es invalida debe retornar error")
    void marcaInvalida_RetornaError(String marca) {
      var input = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", marca, 2016);

      var result = useCase.ejecutar(input);

      assertFalse(result.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "clienteinvalido", "1234", "CLI002" })
    @DisplayName("Si el cliente es invalido o desconocido debe retornar error")
    void clienteInvalidoODesconocido_RetornaError(String cliente) {
      var input = new CrearVehiculoCMD("CYJ691", cliente, "Clio", "CHEVROLET", 2016);

      var result = useCase.ejecutar(input);

      assertFalse(result.isSuccess);
    }

  }
}
