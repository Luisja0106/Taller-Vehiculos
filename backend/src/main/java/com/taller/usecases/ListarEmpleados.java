package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.Empleado;
import com.taller.domain.enums.Rol;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.dto.ListarEmpleadosCMD;

public class ListarEmpleados {

  private final IEmpleadoRepository empleadoRepository;

  public ListarEmpleados(IEmpleadoRepository empleadoRepository) {
    this.empleadoRepository = empleadoRepository;
  }

  public Result<List<Empleado>, IErrorApp> ejecutar(ListarEmpleadosCMD input) {
    if (input == null || input.rol() == null) {
      return listarTodos();
    }
    var rol = Rol.buscarPorNombre(input.rol());
    if (rol.isEmpty()) {
      return Result.error(new ActionError("Error, rol invalido"));
    }
    List<Empleado> list = empleadoRepository.findByRol(rol.get());
    if (list == null) {
      return Result.error(new ActionError("Error al obtener la lista de empleados"));
    }
    return Result.success(list);
  }

  private Result<List<Empleado>, IErrorApp> listarTodos() {
    List<Empleado> list = empleadoRepository.listarTodos();
    if (list == null) {
      return Result.error(new ActionError("Error al obtener la lista de empleados"));
    }
    return Result.success(list);
  }
}
