package com.taller.domain.interfaces;

import com.taller.domain.entities.*;

public sealed interface ResultadoBusqueda
    permits ResultadoBusqueda.EmpleadoEncontrado,
    ResultadoBusqueda.ClienteEncontrado,
    ResultadoBusqueda.OrdenEncontrada,
    ResultadoBusqueda.ServicioEncontrado,
    ResultadoBusqueda.VehiculoEncontrado {
  public record EmpleadoEncontrado(Empleado empleado) implements ResultadoBusqueda {
  }

  public record ClienteEncontrado(Cliente cliente) implements ResultadoBusqueda {
  }

  public record OrdenEncontrada(OrdenDeTrabajo orden) implements ResultadoBusqueda {
  }

  public record ServicioEncontrado(Servicio servicio) implements ResultadoBusqueda {
  }

  public record VehiculoEncontrado(Vehiculo vehiculo) implements ResultadoBusqueda {
  }
}
