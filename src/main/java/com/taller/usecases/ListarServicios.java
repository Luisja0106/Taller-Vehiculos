package com.taller.usecases;

import java.util.List;

import com.taller.domain.entities.Servicio;
import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.domain.utils.Result;

public class ListarServicios {

  private final IServicioRepository servicioRepository;

  public ListarServicios(IServicioRepository servicioRepository) {
    this.servicioRepository = servicioRepository;
  }

  public Result<List<Servicio>, IErrorApp> ejecutar() {
    List<Servicio> list = servicioRepository.listarTodos();

    if (list == null) {
      return Result.error(new ActionError("Error al obtener la lista de sentidos"));
    }
    return Result.success(list);
  }

}
