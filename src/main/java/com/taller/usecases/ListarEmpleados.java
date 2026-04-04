package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.Empleado;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.utils.Result;

public class ListarEmpleados {

  private final IEmpleadoRepository empleadoRepository;

  public ListarEmpleados(IEmpleadoRepository empleadoRepository) {
    this.empleadoRepository = empleadoRepository;
  }

  public Result<List<Empleado>, IErrorApp> ejecutar() {
    List<Empleado> list = empleadoRepository.listarTodos();
    if (list == null) {
      return Result.error(new ActionError("Error al obtener la lista de empleados"));
    }
    return Result.success(list);
  }
}
