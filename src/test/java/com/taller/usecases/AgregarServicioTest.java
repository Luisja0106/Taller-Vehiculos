package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.entities.Servicio;
import com.taller.usecases.dto.AgregarServicioCMD;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.CrearServicioCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;
import com.taller.usecases.fakes.OrdenRepositoryFake;
import com.taller.usecases.fakes.ServicioRepositoryFake;
import com.taller.usecases.fakes.VehiculoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
      var input = new AgregarServicioCMD("ORD20260420001", "SRV001");
      var resultado = useCase.ejecutar(input);

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertTrue(orden.getServicios().get(0).getId().equals(servicio.getId())));
    }
  }
}
