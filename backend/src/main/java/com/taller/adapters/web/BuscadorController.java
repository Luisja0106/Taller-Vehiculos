package com.taller.adapters.web;

import java.util.Map;

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

    return ResponseEntity.ok(resultado.getValue());
  }
}
