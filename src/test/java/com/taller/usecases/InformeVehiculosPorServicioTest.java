package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class InformeVehiculosPorServicioTest {

  OrdenRepositoryFake ordenRepo;
  InformeVehiculosPorServicio useCase;

  @BeforeEach
  void setUp() {
    ordenRepo = new OrdenRepositoryFake();
    useCase = new InformeVehiculosPorServicio(ordenRepo);

    var clienteRepo = new ClienteRepositoryFake();
    var registrarCliente = new RegistrarCliente(clienteRepo);
    registrarCliente.ejecutar(new CrearClienteCMD("Luis", "3208142119", "correo1@correo.com"));

    var vehiculoRepo = new VehiculoRepositoryFake();
    var registrarVehiculo = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    registrarVehiculo.ejecutar(new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "Chevrolet", 2017));
    registrarVehiculo.ejecutar(new CrearVehiculoCMD("ABC123", "CLI001", "Spark", "Chevrolet", 2020));

    var empleadoRepo = new EmpleadoRepositoryFake();
    var contratarEmpleado = new ContratarEmpleado(empleadoRepo);
    contratarEmpleado.ejecutar(new CrearEmpleadoCMD("Alexis", "3208142119", "emp1@correo.com", "mecanico", "fijo"));

    var servicioRepo = new ServicioRepositoryFake();
    var crearServicio = new CrearServicio(servicioRepo);
    crearServicio.ejecutar(new CrearServicioCMD("Cambio de aceite", "150000"));
    crearServicio.ejecutar(new CrearServicioCMD("Cambio de bujia", "200000"));

    var crearOrden = new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);
    var orden1 = crearOrden.ejecutar(new CrearOrdenCMD("CYJ691", "EMP001")).getValue();
    var orden2 = crearOrden.ejecutar(new CrearOrdenCMD("ABC123", "EMP001")).getValue();

    var agregarServicio = new AgregarServicio(servicioRepo, ordenRepo);
    agregarServicio.ejecutar(new AgregarServicioCMD(orden1.getID(), "SRV001"));
    agregarServicio.ejecutar(new AgregarServicioCMD(orden1.getID(), "SRV002"));
    agregarServicio.ejecutar(new AgregarServicioCMD(orden2.getID(), "SRV001"));
  }

  @Nested
  @DisplayName("Informe Exitoso")
  class InformeExitoso {

    @Test
    @DisplayName("Debe retornar el informe correctamente")
    void informeCorrecto_RetornaExito() {
      var resultado = useCase.ejecutar();
      assertTrue(resultado.isSuccess);
    }

    @Test
    @DisplayName("El servicio con mas vehiculos debe estar primero")
    void servicioConMasVehiculos_EstarPrimero() {
      var resultado = useCase.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("SRV001", resultado.getValue().get(0).id()),
          () -> assertEquals(2, resultado.getValue().get(0).cantidad()),
          () -> assertEquals("SRV002", resultado.getValue().get(1).id()),
          () -> assertEquals(1, resultado.getValue().get(1).cantidad()));
    }

    @Test
    @DisplayName("Si no hay ordenes, debe retornar una lista vacia")
    void sinOrdenes_RetornaListaVacia() {
      var repoVacio = new OrdenRepositoryFake();
      var useCaseVacio = new InformeVehiculosPorServicio(repoVacio);
      var resultado = useCaseVacio.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(0, resultado.getValue().size()));
    }
  }
}
