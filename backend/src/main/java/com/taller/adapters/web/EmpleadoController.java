package com.taller.adapters.web;

import java.util.Map;

import com.taller.adapters.web.dto.EmpleadoDTO;
import com.taller.usecases.ActualizarEmpleado;
import com.taller.usecases.BuscarEmpleadoPorEmail;
import com.taller.usecases.BuscarEmpleadoPorId;
import com.taller.usecases.ContratarEmpleado;
import com.taller.usecases.ListarEmpleados;
import com.taller.usecases.RemoveEmpleado;
import com.taller.usecases.dto.ActualizarEmpleadoCMD;
import com.taller.usecases.dto.BuscarEmpleadoPorIdCMD;
import com.taller.usecases.dto.BuscarPorEmailCMD;
import com.taller.usecases.dto.CrearEmpleadoCMD;
import com.taller.usecases.dto.ListarEmpleadosCMD;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

  private final ContratarEmpleado contratarEmpleado;
  private final ActualizarEmpleado actualizarEmpleado;
  private final ListarEmpleados listarEmpleados;
  private final BuscarEmpleadoPorId buscarEmpleadoPorId;
  private final RemoveEmpleado removeEmpleado;
  private final BuscarEmpleadoPorEmail buscarEmpleadoPorEmail;

  public EmpleadoController(ContratarEmpleado contratarEmpleado, ActualizarEmpleado actualizarEmpleado,
      ListarEmpleados listarEmpleados, BuscarEmpleadoPorId buscarEmpleadoPorId, RemoveEmpleado removeEmpleado,
      BuscarEmpleadoPorEmail buscarEmpleadoPorEmail) {
    this.contratarEmpleado = contratarEmpleado;
    this.actualizarEmpleado = actualizarEmpleado;
    this.listarEmpleados = listarEmpleados;
    this.buscarEmpleadoPorId = buscarEmpleadoPorId;
    this.removeEmpleado = removeEmpleado;
    this.buscarEmpleadoPorEmail = buscarEmpleadoPorEmail;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String rol) {
    var resultado = listarEmpleados.ejecutar(new ListarEmpleadosCMD(rol));
    if (!resultado.isSuccess) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    var dtos = resultado.getValue().stream()
        .map(EmpleadoDTO::from)
        .toList();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable String id) {
    var input = new BuscarEmpleadoPorIdCMD(id);
    var resultado = buscarEmpleadoPorId.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.status(404)
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(EmpleadoDTO.from(resultado.getValue()));
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
    var input = new BuscarPorEmailCMD(email);
    var resultado = buscarEmpleadoPorEmail.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.status(404)
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(EmpleadoDTO.from(resultado.getValue()));
  }

  @PostMapping
  public ResponseEntity<?> contratar(@RequestBody CrearEmpleadoCMD input) {
    var resultado = contratarEmpleado.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(201).body(EmpleadoDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody ActualizarEmpleadoCMD input) {
    var resultado = actualizarEmpleado.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(EmpleadoDTO.from(resultado.getValue()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(@PathVariable String id) {
    var resultado = removeEmpleado.ejecutar(id);
    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    return ResponseEntity.status(204).build();

  }

}
