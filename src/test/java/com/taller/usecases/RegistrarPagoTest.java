package com.taller.usecases;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.dto.RegistrarPagoCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;
import com.taller.usecases.fakes.OrdenRepositoryFake;
import com.taller.usecases.fakes.VehiculoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RegistrarPagoTest {

  IOrdenRepository ordenRepo;
  RegistrarPago useCase;
  OrdenDeTrabajo orden;

  @BeforeEach
  void setUp() {
    ordenRepo = new OrdenRepositoryFake();
    useCase = new RegistrarPago(ordenRepo);

    var clienteRepo = new ClienteRepositoryFake();
    var crearClienteUseCase = new RegistrarCliente(clienteRepo);
    crearClienteUseCase.ejecutar(new CrearClienteCMD("Luis", "3102790845", "correo@correo.com"));

    var vehiculoRepo = new VehiculoRepositoryFake();
    var crearVehiculoRepo = new RegistrarVehiculo(vehiculoRepo, clienteRepo);
    crearVehiculoRepo.ejecutar(new CrearVehiculoCMD("CYJ691", "CLI001", "Clio", "chevrolet", 2015));

    var empleadoRepo = new EmpleadoRepositoryFake();
    var crearEmpleadoUseCase = new ContratarEmpleado(empleadoRepo);
    crearEmpleadoUseCase
        .ejecutar(new CrearEmpleadoCMD("Alexis", "3208142119", "correo2@correo.com", "mecanico", "fijo"));

    var crearOrdenUseCase = new CrearOrden(ordenRepo, vehiculoRepo, empleadoRepo);
    orden = crearOrdenUseCase.ejecutar(new CrearOrdenCMD("CYJ691", "EMP001")).getValue();
  }

  @Nested
  @DisplayName("Caso Exitoso")
  class CasoExitoso {

    @Test
    @DisplayName("Si el input es correcto, debe registrar")
    void inputCorrecto_Registra() {
      var input = new RegistrarPagoCMD(orden.getID(), "150000");
    }
  }
}
