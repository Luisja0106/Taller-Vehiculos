package com.taller.adapters.web;

import java.util.Map;

import com.taller.usecases.ActualizarEmpleado;
import com.taller.usecases.ContratarEmpleado;
import com.taller.usecases.ListarEmpleados;
import com.taller.usecases.dto.ActualizarEmpleadoCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

  private final ContratarEmpleado contratarEmpleado;
  private final ActualizarEmpleado actualizarEmpleado;
  private final ListarEmpleados listarEmpleados;

  public EmpleadoController(ContratarEmpleado contratarEmpleado, ActualizarEmpleado actualizarEmpleado,
      ListarEmpleados listarEmpleados) {
    this.contratarEmpleado = contratarEmpleado;
    this.actualizarEmpleado = actualizarEmpleado;
    this.listarEmpleados = listarEmpleados;
  }

  @GetMapping
  public ResponseEntity<?> listar() {
    var resultado = listarEmpleados.ejecutar();
    if (!resultado.isSuccess) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(resultado.getValue());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable String id) {
    // TODO: create the empleado buscar por id use case and implement here
    // var empleado =
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> contratar(@RequestBody CrearEmpleadoCMD input) {
    var resultado = contratarEmpleado.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(201).body(resultado.getValue());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody ActualizarEmpleadoCMD input) {
    // TODO:
    // need to inject the id from the URL into the cmd
    // ActualizarEmpleadoCMD needs the id
    var resultado = actualizarEmpleado.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(resultado.getValue());
  }

}
