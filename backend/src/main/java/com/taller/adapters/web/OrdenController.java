package com.taller.adapters.web;

import java.util.Map;

import com.taller.adapters.persistence.jpa.implementations.OrdenRepositoryImpl;
import com.taller.adapters.web.dto.OrdenDTO;
import com.taller.usecases.AgregarServicio;
import com.taller.usecases.AvanzarEstadoDeOrden;
import com.taller.usecases.BuscarOrdenPorId;
import com.taller.usecases.CrearOrden;
import com.taller.usecases.EliminarServicioDeOrden;
import com.taller.usecases.ListarOrdenes;
import com.taller.usecases.ObtenerOrdenesPorServicioId;
import com.taller.usecases.ReasignarEmpleadoAOrden;
import com.taller.usecases.RegistrarPago;
import com.taller.usecases.RemoveOrden;
import com.taller.usecases.dto.AgregarServicioCMD;
import com.taller.usecases.dto.AvanzarEstadoDeOrdenCMD;
import com.taller.usecases.dto.BuscarOrdenPorIdCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.EliminarServicioDeOrdenCMD;
import com.taller.usecases.dto.ListarOrdenesCMD;
import com.taller.usecases.dto.ReasignarEmpleadoAOrdenCMD;
import com.taller.usecases.dto.RegistrarPagoCMD;

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
@RequestMapping("/api/ordenes")
public class OrdenController {

  private final CrearOrden crearOrden;
  private final ListarOrdenes listarOrdenes;
  private final AvanzarEstadoDeOrden avanzarEstadoDeOrden;
  private final AgregarServicio agregarServicio;
  private final ReasignarEmpleadoAOrden reasignarEmpleadoAOrden;
  private final RegistrarPago registrarPago;
  private final BuscarOrdenPorId buscarOrdenPorId;
  private final RemoveOrden removeOrden;
  private final ObtenerOrdenesPorServicioId obtenerOrdenesPorServicioId;
  private final EliminarServicioDeOrden eliminarServicioDeOrden;
  private final OrdenRepositoryImpl ordenRepo;

  public OrdenController(CrearOrden crearOrden, ListarOrdenes listarOrdenes,
      AvanzarEstadoDeOrden avanzarEstadoDeOrden, AgregarServicio agregarServicio,
      ReasignarEmpleadoAOrden reasignarEmpleadoAOrden, RegistrarPago registrarPago, BuscarOrdenPorId buscarOrdenPorId,
      RemoveOrden removeOrden, ObtenerOrdenesPorServicioId obtenerOrdenesPorServicioId,
      EliminarServicioDeOrden eliminarServicioDeOrden, OrdenRepositoryImpl ordenRepo) {
    this.crearOrden = crearOrden;
    this.listarOrdenes = listarOrdenes;
    this.avanzarEstadoDeOrden = avanzarEstadoDeOrden;
    this.agregarServicio = agregarServicio;
    this.reasignarEmpleadoAOrden = reasignarEmpleadoAOrden;
    this.registrarPago = registrarPago;
    this.buscarOrdenPorId = buscarOrdenPorId;
    this.removeOrden = removeOrden;
    this.obtenerOrdenesPorServicioId = obtenerOrdenesPorServicioId;
    this.eliminarServicioDeOrden = eliminarServicioDeOrden;
    this.ordenRepo = ordenRepo;
  }

  @GetMapping
  public ResponseEntity<?> listar(
      @RequestParam(required = false) String estado,
      @RequestParam(required = false) String placaVehiculo,
      @RequestParam(required = false) String empleadoId) {

    var input = new ListarOrdenesCMD(estado, placaVehiculo, empleadoId);
    var resultado = listarOrdenes.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    var dtos = resultado.getValue().stream()
        .map(OrdenDTO::from)
        .toList();

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable String id) {
    var input = new BuscarOrdenPorIdCMD(id);
    var resultado = buscarOrdenPorId.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(OrdenDTO.from(resultado.getValue()));
  }

  @GetMapping("/servicio/{servicioId}")
  public ResponseEntity<?> buscarPorServicio(@PathVariable String servicioId) {
    var resultado = obtenerOrdenesPorServicioId.ejecutar(servicioId);

    if (!resultado.isSuccess) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    var dtos = resultado.getValue().stream()
        .map(OrdenDTO::from)
        .toList();

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/activas")
  public ResponseEntity<?> listarOrdenesActivas(
      @RequestParam(required = false) String placaVehiculo,
      @RequestParam(required = false) String empleadoId) {
    var ordenes = ordenRepo.listarOrdenesActivas(empleadoId, placaVehiculo);

    return ResponseEntity.ok(ordenes.stream()
        .map(OrdenDTO::from)
        .toList());
  }

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody CrearOrdenCMD input) {
    var resultado = crearOrden.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(201).body(OrdenDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{id}/avanzar")
  public ResponseEntity<?> avanzarEstado(@PathVariable String id) {
    var input = new AvanzarEstadoDeOrdenCMD(id);
    var resultado = avanzarEstadoDeOrden.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(OrdenDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{id}/servicios")
  public ResponseEntity<?> agregarServicio(@PathVariable String id, @RequestBody AgregarServicioCMD input) {
    var resultado = agregarServicio.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(OrdenDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{id}/empleado")
  public ResponseEntity<?> reasignarEmpleado(@PathVariable String id, @RequestBody ReasignarEmpleadoAOrdenCMD input) {
    var resultado = reasignarEmpleadoAOrden.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(OrdenDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{id}/pago")
  public ResponseEntity<?> registrarPago(@PathVariable String id, @RequestBody RegistrarPagoCMD input) {
    var resultado = registrarPago.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(OrdenDTO.from(resultado.getValue()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarOrden(@PathVariable String id) {
    var resu = removeOrden.ejecutar(id);
    if (!resu.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resu.getError().getMessage()));
    }

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}/servicios/{servicioId}")
  public ResponseEntity<?> eliminarServicio(@PathVariable String id, @PathVariable String servicioId) {
    var input = new EliminarServicioDeOrdenCMD(id, servicioId);
    var resu = eliminarServicioDeOrden.ejecutar(input);
    if (!resu.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resu.getError().getMessage()));
    }

    return ResponseEntity.ok(OrdenDTO.from(resu.getValue()));
  }

}
