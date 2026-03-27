package com.taller.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.taller.domain.enums.Marca;
import com.taller.domain.enums.Rol;
import com.taller.domain.enums.TipoDeContrato;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

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
      assertTrue(vehiculo.isSuccess, () -> "Vehiculo erroneo " + cliente.getError().getMessage());

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

      // el dueño tiene el vehiculo correcto
      assertTrue(cliente.getValue().getVehiculos().contains(vehiculo.getValue()),
          "el cliente no cuenta con el vehiculo adecuado");

    }
  }

}
