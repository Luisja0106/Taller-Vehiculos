package com.taller.adapters.web;

import java.util.Map;

import com.taller.adapters.web.dto.ClienteDTO;
import com.taller.adapters.web.dto.EmpleadoDTO;
import com.taller.adapters.web.dto.OrdenDTO;
import com.taller.adapters.web.dto.ServicioDTO;
import com.taller.adapters.web.dto.VehiculoDTO;
import com.taller.domain.interfaces.ResultadoBusqueda;
import com.taller.usecases.BuscarPorCodigo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buscar")
public class BuscadorController {

  private final BuscarPorCodigo buscarPorCodigo;

  public BuscadorController(BuscarPorCodigo buscarPorCodigo) {
    this.buscarPorCodigo = buscarPorCodigo;
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
    var resultado = buscarPorCodigo.ejecutar(codigo);

    if (!resultado.isSuccess) {
      return ResponseEntity.status(404)
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    return switch (resultado.getValue()) {
      case ResultadoBusqueda.EmpleadoEncontrado e ->
        ResponseEntity.ok(EmpleadoDTO.from(e.empleado()));
      case ResultadoBusqueda.ClienteEncontrado c ->
        ResponseEntity.ok(ClienteDTO.from(c.cliente()));
      case ResultadoBusqueda.VehiculoEncontrado v ->
        ResponseEntity.ok(VehiculoDTO.from(v.vehiculo()));
      case ResultadoBusqueda.ServicioEncontrado s ->
        ResponseEntity.ok(ServicioDTO.from(s.servicio()));
      case ResultadoBusqueda.OrdenEncontrada o ->
        ResponseEntity.ok(OrdenDTO.from(o.orden()));
    };
  }
}
