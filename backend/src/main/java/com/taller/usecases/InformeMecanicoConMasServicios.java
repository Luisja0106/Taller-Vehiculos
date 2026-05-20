package com.taller.usecases;

import java.util.List;

import com.taller.domain.errors.ActionError;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.output.EntidadConteo;

public class InformeMecanicoConMasServicios {
  private final IOrdenRepository ordenRepository;

  public InformeMecanicoConMasServicios(IOrdenRepository ordenRepository) {
    this.ordenRepository = ordenRepository;
  }

  public Result<List<EntidadConteo>, IErrorApp> ejecutar() {
    List<EntidadConteo> result = ordenRepository.mecanicoConMasServicio();
    if (result == null) {
      return Result.error(new ActionError("Error al obtener el reporte"));
    }
    return Result.success(result);
  }
}
