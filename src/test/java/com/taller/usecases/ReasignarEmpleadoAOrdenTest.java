package com.taller.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taller.domain.entities.Empleado;
import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.usecases.dto.CrearClienteCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.dto.ReasignarEmpleadoAOrdenCMD;
import com.taller.usecases.fakes.ClienteRepositoryFake;
import com.taller.usecases.fakes.EmpleadoRepositoryFake;
import com.taller.usecases.fakes.OrdenRepositoryFake;
import com.taller.usecases.fakes.VehiculoRepositoryFake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReasignarEmpleadoAOrdenTest {
  IEmpleadoRepository empleadoRepository;
  IOrdenRepository ordenRepository;
  ReasignarEmpleadoAOrden useCase;
  OrdenDeTrabajo orden;
  Empleado nuevoEmpleado;

  @BeforeEach
  void setUp() {
    empleadoRepository = new EmpleadoRepositoryFake();
    ordenRepository = new OrdenRepositoryFake();

    useCase = new ReasignarEmpleadoAOrden(empleadoRepository, ordenRepository);

    var crearEmpleadoUseCase = new ContratarEmpleado(empleadoRepository);
    crearEmpleadoUseCase.ejecutar(new CrearEmpleadoCMD("Luis", "3102790846", "correo@correo.com", "mecanico", "fijo"));

    var clienteRepository = new ClienteRepositoryFake();
    var crearClienteUseCase = new RegistrarCliente(clienteRepository);
    crearClienteUseCase.ejecutar(new CrearClienteCMD("Alexis", "3208142119", "correo@2correo.com"));

    var vehiculoRepository = new VehiculoRepositoryFake();
    var crearVehiculoUseCase = new RegistrarVehiculo(vehiculoRepository, clienteRepository);
    crearVehiculoUseCase.ejecutar(new CrearVehiculoCMD("CYJ691", "CLI001", "clio", "mazda", 2015));

    var crearOrdenUseCase = new CrearOrden(ordenRepository, vehiculoRepository, empleadoRepository);
    orden = crearOrdenUseCase.ejecutar(new CrearOrdenCMD("CYJ691", "EMP001")).getValue();

    nuevoEmpleado = crearEmpleadoUseCase
        .ejecutar(new CrearEmpleadoCMD("Thomas", "3056442332", "correo@correo.com", "mecanico", "fijo")).getValue();
  }

  @Nested
  @DisplayName("Caso Exitoso")
  class CasoExitoso {

    @Test
    @DisplayName("Si el input es correcto se actualiza el empleado")
    void inputValido_ActualizacionExitosa() {
      var input = new ReasignarEmpleadoAOrdenCMD(orden.getID(), nuevoEmpleado.getId());
      var resultado = useCase.ejecutar(input);
      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(nuevoEmpleado.getId(), orden.getEmpleadoACargo().getId()));
    }

    @Test
    @DisplayName("Se actualiza el empleado en el repositorio")
    void actualizacionExitosa_SeActualizaEnElRepositorio() {
      var input = new ReasignarEmpleadoAOrdenCMD(orden.getID(), nuevoEmpleado.getId());
      var resultado = useCase.ejecutar(input);
      assertAll(
          () -> assertTrue(resultado.isSuccess),
          () -> assertEquals(nuevoEmpleado.getId(), orden.getEmpleadoACargo().getId()),
          () -> assertEquals(nuevoEmpleado.getId(),
              ordenRepository.buscarPorId(nuevoEmpleado.getId()).get().getEmpleadoACargo().getId()));
    }
  }
}
