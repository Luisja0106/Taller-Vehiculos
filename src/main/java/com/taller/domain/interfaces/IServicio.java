package com.taller.domain.interfaces;

import java.math.BigDecimal;

import com.taller.domain.utils.Result;

public interface IServicio {

  public String getNombreDelServicio();

  public Result<BigDecimal, IErrorApp> calcularCosto();

}
