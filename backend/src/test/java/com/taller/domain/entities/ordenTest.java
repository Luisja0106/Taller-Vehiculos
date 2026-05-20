package com.taller.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.enums.Marca;
import com.taller.domain.enums.Rol;
import com.taller.domain.enums.TipoDeContrato;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ordenTest {

  @Nested
  @DisplayName("si se crea exitosamente")
  class siSeCreaExitosamente {

    @Test
    @DisplayName("parametros correctos")
    void parametrosCorrectos() {

      // cliente
      Result<Cliente, IErrorApp> cliente = Cliente.crear("CLI001", "Alexis", "3208142119", "luisjaperez0106@gmail.com");
      assertTrue(cliente.isSuccess, () -> "Cliente erroneo " + cliente.getError().getMessage());

      // vehiculo
      Result<Vehiculo, IErrorApp> vehiculo = Vehiculo.crear("CYJ691", cliente.getValue(), "clio", Marca.RENAULT, 2007);
      assertTrue(vehiculo.isSuccess, () -> "Vehiculo erroneo " + vehiculo.getError().getMessage());

      // Empleado
      Result<Empleado, IErrorApp> empleado = Empleado.crear("EMP001", "Luis Javier", "3208142119",
          "luisjaperez0106@gmail.com", Rol.MECANICO, TipoDeContrato.FIJO);
      assertTrue(empleado.isSuccess, () -> "Empleado erroneo " + empleado.getError().getMessage());

      // Orden
      Result<OrdenDeTrabajo, IErrorApp> orden = OrdenDeTrabajo.crear("ORD001", vehiculo.getValue(),
          empleado.getValue());
      assertTrue(orden.isSuccess, () -> "Orden erronea " + orden.getError().getMessage());
    }

    @Test
    @DisplayName("El carro deberia asignar bien a su dueño")
    void asignacionCorrectaDeVehiculos() {
      // cliente
      Result<Cliente, IErrorApp> cliente = Cliente.crear("CLI001", "Alexis", "3208142119", "luisjaperez0106@gmail.com");

      // vehiculo
      Result<Vehiculo, IErrorApp> vehiculo = Vehiculo.crear("CYJ691", cliente.getValue(), "clio", Marca.RENAULT, 2007);

      // el vehiculo tiene su dueño correcto
      assertEquals(vehiculo.getValue().getDueño(), cliente.getValue(), "el vehiculo no tiene el cliente adecuado");

    }
  }

  @Nested
  @DisplayName("Si no se crea correctamente")
  class siNoSeCreaCorrectamente {

    @Test
    @DisplayName("id null, retorna error")
    void idNullRetornaError() {
      Result<Cliente, IErrorApp> cliente = Cliente.crear("CLI001", "Alexis", "3208142119", "luisjaperez0106@gmail.com");
      assertTrue(cliente.isSuccess, () -> "Cliente erroneo " + cliente.getError().getMessage());

      // vehiculo
      Result<Vehiculo, IErrorApp> vehiculo = Vehiculo.crear("CYJ691", cliente.getValue(), "clio", Marca.RENAULT, 2007);
      assertTrue(vehiculo.isSuccess, () -> "Vehiculo erroneo " + vehiculo.getError().getMessage());

      // Empleado
      Result<Empleado, IErrorApp> empleado = Empleado.crear("EMP001", "Luis Javier", "3208142119",
          "luisjaperez0106@gmail.com", Rol.MECANICO, TipoDeContrato.FIJO);
      assertTrue(empleado.isSuccess, () -> "Empleado erroneo " + empleado.getError().getMessage());

      Result<OrdenDeTrabajo, IErrorApp> orden = OrdenDeTrabajo.crear(null, vehiculo.getValue(), empleado.getValue());

      assertFalse(orden.isSuccess);
      assertEquals("Error el id no puede ser vacio", orden.getError().getMessage());

    }

    @Test
    @DisplayName("vehiculo null, retorna error")
    void vehiculoNullRetornaError() {
      // Empleado
      Result<Empleado, IErrorApp> empleado = Empleado.crear("EMP001", "Luis Javier", "3208142119",
          "luisjaperez0106@gmail.com", Rol.MECANICO, TipoDeContrato.FIJO);
      assertTrue(empleado.isSuccess, () -> "Empleado erroneo " + empleado.getError().getMessage());

      Result<OrdenDeTrabajo, IErrorApp> orden = OrdenDeTrabajo.crear("ORD001", null, empleado.getValue());

      assertFalse(orden.isSuccess);
      assertEquals("Error el vehiculo es invalido", orden.getError().getMessage());

    }

    @Test
    @DisplayName("empleado null, retorna error")
    void empleadoNullRetornaError() {
      Result<Cliente, IErrorApp> cliente = Cliente.crear("CLI001", "Alexis", "3208142119", "luisjaperez0106@gmail.com");
      assertTrue(cliente.isSuccess, () -> "Cliente erroneo " + cliente.getError().getMessage());

      // vehiculo
      Result<Vehiculo, IErrorApp> vehiculo = Vehiculo.crear("CYJ691", cliente.getValue(), "clio", Marca.RENAULT, 2007);
      assertTrue(vehiculo.isSuccess, () -> "Vehiculo erroneo " + vehiculo.getError().getMessage());

      Result<OrdenDeTrabajo, IErrorApp> orden = OrdenDeTrabajo.crear("ORD001", vehiculo.getValue(), null);

      assertFalse(orden.isSuccess);
      assertEquals("Error el empleado a cargo no puede ser nulo", orden.getError().getMessage());
    }

  }

  @Nested
  @DisplayName("maquina de estados")
  class maquinaDeEstados {

    private OrdenDeTrabajo orden;

    @BeforeEach
    void setUp() {
      Result<Cliente, IErrorApp> cliente = Cliente.crear(
          "CLI001", "prueba", "3001234567", "correo@mail.com");
      Result<Vehiculo, IErrorApp> vehiculo = Vehiculo.crear(
          "CYJ691", cliente.getValue(), "clio", Marca.RENAULT, 2007);
      Result<Empleado, IErrorApp> empleado = Empleado.crear(
          "EMP001", "prueba", "3001234567", "correo@mail.com",
          Rol.MECANICO, TipoDeContrato.FIJO);
      orden = OrdenDeTrabajo.crear(
          "ORD001", vehiculo.getValue(), empleado.getValue()).getValue();
    }

    @Test
    @DisplayName("PENDIENTE avanza a EN_PROCESO")
    void pendienteAvanzaAEnProceso() {
      var resultado = orden.avanzarEstado();
      assertTrue(resultado.isSuccess);
      assertEquals(EstadoDelTrabajo.EN_PROCESO, orden.getEstado());
    }

    @Test
    @DisplayName("EN_PROCESO avanza a EN_ESPERA_DE_PAGO y registra fecha")
    void enProcesoAvanzaAEnEsperaDePago() {
      orden.avanzarEstado(); // PENDIENTE → EN_PROCESO
      var resultado = orden.avanzarEstado(); // EN_PROCESO → EN_ESPERA_DE_PAGO
      assertTrue(resultado.isSuccess);
      assertEquals(EstadoDelTrabajo.EN_ESPERA_DE_PAGO, orden.getEstado());
      assertNotNull(orden.getFechaDeFinalizacion()); // date was set automatically
    }

    @Test
    @DisplayName("FINALIZADO no puede avanzar")
    void finalizadoNoPuedeAvanzar() {
      orden.avanzarEstado(); // PENDIENTE → EN_PROCESO
      orden.avanzarEstado(); // EN_PROCESO → EN_ESPERA_DE_PAGO
      orden.registrarPago(new BigDecimal("150000"));
      orden.avanzarEstado(); // EN_ESPERA_DE_PAGO → FINALIZADO
      var resultado = orden.avanzarEstado(); // should fail
      assertFalse(resultado.isSuccess);
    }

    @Test
    @DisplayName("no puede avanzar a FINALIZADO sin pago registrado")
    void noAvanzaAFinalizadoSinPago() {
      orden.avanzarEstado(); // PENDIENTE → EN_PROCESO
      orden.avanzarEstado(); // EN_PROCESO → EN_ESPERA_DE_PAGO
      var resultado = orden.avanzarEstado(); // should fail, no payment
      assertFalse(resultado.isSuccess);
    }
  }

}
