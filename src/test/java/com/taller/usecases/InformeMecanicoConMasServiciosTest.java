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

class InformeMecanicoConMasServiciosTest {

  OrdenRepositoryFake ordenRepo;
  InformeMecanicoConMasServicios useCase;

  @BeforeEach
  void setUp() {
    ordenRepo = new OrdenRepositoryFake();
    useCase = new InformeMecanicoConMasServicios(ordenRepo);

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
    contratarEmpleado.ejecutar(new CrearEmpleadoCMD("Pedro", "3108142119", "emp2@correo.com", "mecanico", "parcial"));

    var servicioRepo = new ServicioRepositoryFake();
    var crearServicio = new CrearServicio(servicioRepo);
    crearServicio.ejecutar(new CrearServicioCMD("Cambio de aceite", "150000"));

    var crearOrden = new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);
    var orden1 = crearOrden.ejecutar(new CrearOrdenCMD("CYJ691", "EMP001")).getValue();
    var orden2 = crearOrden.ejecutar(new CrearOrdenCMD("ABC123", "EMP001")).getValue();
    var orden3 = crearOrden.ejecutar(new CrearOrdenCMD("CYJ691", "EMP002")).getValue();

    var agregarServicio = new AgregarServicio(servicioRepo, ordenRepo);
    agregarServicio.ejecutar(new AgregarServicioCMD(orden1.getID(), "SRV001"));
    agregarServicio.ejecutar(new AgregarServicioCMD(orden2.getID(), "SRV001"));
    agregarServicio.ejecutar(new AgregarServicioCMD(orden3.getID(), "SRV001"));
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
    @DisplayName("El mecanico con mas ordenes debe estar primero")
    void mecanicoConMasOrdenes_EstarPrimero() {
      var resultado = useCase.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals("EMP001", resultado.getValue().get(0).id()),
          () -> assertEquals(2, resultado.getValue().get(0).cantidad()));
    }

    @Test
    @DisplayName("Si no hay ordenes, debe retornar una lista vacia")
    void sinOrdenes_RetornaListaVacia() {
      var repoVacio = new OrdenRepositoryFake();
      var useCaseVacio = new InformeMecanicoConMasServicios(repoVacio);
      var resultado = useCaseVacio.ejecutar();

      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(0, resultado.getValue().size()));
    }
  }
}
