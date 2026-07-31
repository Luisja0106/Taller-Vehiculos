package com.taller.adapters.web;

import java.util.Map;

import com.taller.adapters.web.dto.ServicioDTO;
import com.taller.domain.repositories.IServicioRepository;
import com.taller.usecases.ActualizarServicio;
import com.taller.usecases.BuscarServicioPorId;
import com.taller.usecases.CrearServicio;
import com.taller.usecases.ListarServicios;
import com.taller.usecases.RemoveServicio;
import com.taller.usecases.dto.ActualizarServicioCMD;
import com.taller.usecases.dto.BuscarServicioPorIdCMD;
import com.taller.usecases.dto.CrearServicioCMD;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  private final BuscarServicioPorId buscarServicioPorId;
  private final RemoveServicio removeServicio;
  private final IServicioRepository servicoRepository;

  public ServicioController(CrearServicio crearServicio, ActualizarServicio actualizarServicio,
      ListarServicios listarServicios, BuscarServicioPorId buscarServicioPorId, RemoveServicio removeServicio,
      IServicioRepository servicoRepository) {
    this.crearServicio = crearServicio;
    this.actualizarServicio = actualizarServicio;
    this.listarServicios = listarServicios;
    this.buscarServicioPorId = buscarServicioPorId;
    this.removeServicio = removeServicio;
    this.servicoRepository = servicoRepository;
  }

  @GetMapping
  public ResponseEntity<?> listar() {
    var resultado = listarServicios.ejecutar();

    if (!resultado.isSuccess) {
      return ResponseEntity.status(404)
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    var dtos = resultado.getValue().stream()
        .map(ServicioDTO::from)
        .toList();

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable String id) {
    var input = new BuscarServicioPorIdCMD(id);
    var resultado = buscarServicioPorId.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.status(404)
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    return ResponseEntity.ok(ServicioDTO.from(resultado.getValue()));
  }

  @GetMapping("/nombre/{nombre}")
  public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
    var resultado = servicoRepository.buscarPorNombre(nombre);

    if (resultado.isEmpty()) {
      return ResponseEntity.status(404)
          .body(Map.of("error", "Servicio no encontrado con el nombre: " + nombre));
    }

    return ResponseEntity.ok(ServicioDTO.from(resultado.get()));
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody CrearServicioCMD input) {
    var resultado = crearServicio.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(201).body(ServicioDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody ActualizarServicioCMD input) {
    var resultado = actualizarServicio.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(ServicioDTO.from(resultado.getValue()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    var resultado = removeServicio.ejecutar(id);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(204).build();

  }

}
