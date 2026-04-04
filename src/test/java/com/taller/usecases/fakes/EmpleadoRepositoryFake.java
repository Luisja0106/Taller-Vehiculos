package com.taller.usecases.fakes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Empleado;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IEmpleadoRepository;
import com.taller.domain.utils.Result;

public class EmpleadoRepositoryFake implements IEmpleadoRepository {

  private final List<Empleado> empleados = new ArrayList<>();
  private int contador = 0;

  @Override
  public Result<Empleado, IErrorApp> guardar(Empleado empleado) {
    empleados.add(empleado);
    return Result.success(empleado);
  }

  @Override
  public Result<Empleado, IErrorApp> actualizar(Empleado empleado) {
    empleados.removeIf(e -> e.getId().equals(empleado.getId()));
    empleados.add(empleado);
    return Result.success(empleado);
  }

  @Override
  public Optional<Empleado> buscarPorId(String id) {
    return empleados.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst();
  }

  @Override
  public Optional<Empleado> buscarPorEmail(String email) {
    return empleados.stream()
        .filter(e -> e.getEmail().toString().equals(email))
        .findFirst();
  }

  @Override
  public List<Empleado> listarTodos() {
    return new ArrayList<>(empleados);
  }

  @Override
  public void eliminar(String id) {
    empleados.removeIf(e -> e.getId().equals(id));
  }

  @Override
  public int siguienteNumeroId() {
    return ++contador;
  }

}
