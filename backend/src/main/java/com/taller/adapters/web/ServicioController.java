package com.taller.adapters.web;

import java.util.Map;

import com.taller.usecases.ActualizarServicio;
import com.taller.usecases.CrearServicio;
import com.taller.usecases.ListarServicios;
import com.taller.usecases.dto.ActualizarServicioCMD;
import com.taller.usecases.dto.CrearServicioCMD;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

  private final CrearServicio crearServicio;
  private final ActualizarServicio actualizarServicio;
  private final ListarServicios listarServicios;

  public ServicioController(CrearServicio crearServicio, ActualizarServicio actualizarServicio,
      ListarServicios listarServicios) {
    this.crearServicio = crearServicio;
    this.actualizarServicio = actualizarServicio;
    this.listarServicios = listarServicios;
  }

  @GetMapping
  public ResponseEntity<?> listar() {
    var resultado = listarServicios.ejecutar();

    if (!resultado.isSuccess) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    return ResponseEntity.ok(resultado.getValue());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable String id) {
    // TODO: create the buscar servicio por id use case
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody CrearServicioCMD input) {
    var resultado = crearServicio.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(201).body(resultado.getValue());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody ActualizarServicioCMD input) {
    var resultado = actualizarServicio.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(resultado.getValue());
  }

}
