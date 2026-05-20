package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.entities.Servicio;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.usecases.dto.AgregarServicioCMD;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.CrearServicioCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.fakes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class AgregarServicioTest {
  OrdenRepositoryFake ordenRepo;
  ServicioRepositoryFake servicioRepo;
  AgregarServicio useCase;
  OrdenDeTrabajo orden;
  Servicio servicio;

  @BeforeEach
  void setUp() {
    ordenRepo = new OrdenRepositoryFake();
    servicioRepo = new ServicioRepositoryFake();
    useCase = new AgregarServicio(servicioRepo, ordenRepo);

    ClienteRepositoryFake clienteRepo = new ClienteRepositoryFake();
    var registrarClienteUseCase = new RegistrarCliente(clienteRepo);
    var cliente = new CrearClienteCMD("Luis", "3208142119", "luisjaperez010@gmail.com");
    registrarClienteUseCase.ejecutar(cliente);
    var vehiculoRepo = new VehiculoRepositoryFake();
    var registrarVehiculoUseCase = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    var vehiculo = new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "chevrolet", 2021);
    registrarVehiculoUseCase.ejecutar(vehiculo);

    EmpleadoRepositoryFake empleadoRepo = new EmpleadoRepositoryFake();
    var contratarEmpleadosUseCase = new ContratarEmpleado(empleadoRepo);
    var empleado = new CrearEmpleadoCMD("Alexis", "30102790845", "correo@correo.com", "mecanico", "fijo");
    contratarEmpleadosUseCase.ejecutar(empleado);
    var crearOrdenUseCase = new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);
    var ordenInput = new CrearOrdenCMD("CYJ691", "EMP001");
    orden = crearOrdenUseCase.ejecutar(ordenInput).getValue();

    var crearServicioUseCase = new CrearServicio(servicioRepo);
    var servicioInput = new CrearServicioCMD("Cambio de bujia", "2100000");
    servicio = crearServicioUseCase.ejecutar(servicioInput).getValue();
  }

  @Nested
  @DisplayName("Creacion Exitosa")
  class CreacionExitosa {

    @Test
    @DisplayName("Si el input es correcto, se agrega el servicio correctamente")
    void inputCorrecto_AgregaServicio() {
      var input = new AgregarServicioCMD(orden.getID(), "SRV001");
      var resultado = useCase.ejecutar(input);
      var ordenActualizada = ordenRepo.buscarPorId(orden.getID()).get();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertFalse(ordenActualizada.getServicios().isEmpty()),
          () -> assertEquals(servicio.getId(), ordenActualizada.getServicios().get(0).getId()),
          () -> assertTrue(orden.getServicios().get(0).getId().equals(servicio.getId())));
    }

    @Test
    @DisplayName("Se pueden agregar varios servicios a una misma orden")
    void multiplesServicios_SeAgreganCorrectamente() {
      var crearServicioUseCase = new CrearServicio(servicioRepo);
      crearServicioUseCase.ejecutar(new CrearServicioCMD("Cambio de Aceite", "250000"));

      useCase.ejecutar(new AgregarServicioCMD(orden.getID(), "SRV001"));
      useCase.ejecutar(new AgregarServicioCMD(orden.getID(), "SRV002"));

      var ordenActualizada = ordenRepo.buscarPorId(orden.getID()).get();
      assertEquals(2, ordenActualizada.getServicios().size());
    }
  }

  @Nested
  @DisplayName("Creacion Erronea")
  class creacionErronea {

    @Test
    @DisplayName("Si el input es nulo, debe retornar un error")
    void inputNulo_RetornaError() {

      var resultado = useCase.ejecutar(null);

      assertAll(
          () -> assertFalse(resultado.isSuccess),
          () -> assertEquals("Error los datos no pueden ser nulos", resultado.getError().getMessage()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "ORD002", "asttrstrstrs" })
    @DisplayName("Si la orden no existe, retorna error")
    void ordenInexistente_RetornaError(String orden) {
      var input = new AgregarServicioCMD(orden, "SRV001");
      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "SRV002", "asttrstrstrs" })
    @DisplayName("Si el servicio no existe, retorna error")
    void servicioInexistente_RetornaError(String servicio) {
      var input = new AgregarServicioCMD(orden.getID(), servicio);
      var resultado = useCase.ejecutar(input);

      assertFalse(resultado.isSuccess);
    }

    @Test
    @DisplayName("Si el estado de orden es invalido retorna error")
    void estadoInvalido_RetornaError() {
      orden.avanzarEstado(); // en proceso
      orden.avanzarEstado(); // en espera de pago

      var input = new AgregarServicioCMD(orden.getID(), servicio.getId());
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertEquals(EstadoDelTrabajo.EN_ESPERA_DE_PAGO, orden.getEstado()),
          () -> assertFalse(resultado.isSuccess));
    }

  }
}
