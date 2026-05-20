package com.taller.adapters.web;

import java.util.Map;

import com.taller.adapters.web.dto.OrdenDTO;
import com.taller.usecases.AgregarServicio;
import com.taller.usecases.AvanzarEstadoDeOrden;
import com.taller.usecases.BuscarOrdenPorId;
import com.taller.usecases.CrearOrden;
import com.taller.usecases.ListarOrdenes;
import com.taller.usecases.ReasignarEmpleadoAOrden;
import com.taller.usecases.RegistrarPago;
import com.taller.usecases.dto.AgregarServicioCMD;
import com.taller.usecases.dto.AvanzarEstadoDeOrdenCMD;
import com.taller.usecases.dto.BuscarOrdenPorIdCMD;
import com.taller.usecases.dto.CrearOrdenCMD;
import com.taller.usecases.dto.ListarOrdenesCMD;
import com.taller.usecases.dto.ReasignarEmpleadoAOrdenCMD;
import com.taller.usecases.dto.RegistrarPagoCMD;

import org.springframework.http.ResponseEntity;
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

  public OrdenController(CrearOrden crearOrden, ListarOrdenes listarOrdenes,
      AvanzarEstadoDeOrden avanzarEstadoDeOrden, AgregarServicio agregarServicio,
      ReasignarEmpleadoAOrden reasignarEmpleadoAOrden, RegistrarPago registrarPago, BuscarOrdenPorId buscarOrdenPorId) {
    this.crearOrden = crearOrden;
    this.listarOrdenes = listarOrdenes;
    this.avanzarEstadoDeOrden = avanzarEstadoDeOrden;
    this.agregarServicio = agregarServicio;
    this.reasignarEmpleadoAOrden = reasignarEmpleadoAOrden;
    this.registrarPago = registrarPago;
    this.buscarOrdenPorId = buscarOrdenPorId;
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

  @PostMapping("/{id}/servicios")
  public ResponseEntity<?> agregarServicio(@PathVariable String id, @RequestBody AgregarServicioCMD input) {
    var cmd = new AgregarServicioCMD(id, input.servicioId());
    var resultado = agregarServicio.ejecutar(cmd);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(OrdenDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{id}/empleado")
  public ResponseEntity<?> reasignarEmpleado(@PathVariable String id, @RequestBody ReasignarEmpleadoAOrdenCMD input) {
    var cmd = new ReasignarEmpleadoAOrdenCMD(id, input.nuevoEmpleadoId());
    var resultado = reasignarEmpleadoAOrden.ejecutar(cmd);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(OrdenDTO.from(resultado.getValue()));
  }

  @PostMapping("/{id}/pago")
  public ResponseEntity<?> registrarPago(@PathVariable String id, @RequestBody RegistrarPagoCMD input) {
    var cmd = new RegistrarPagoCMD(id, input.pago());
    var resultado = registrarPago.ejecutar(cmd);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(OrdenDTO.from(resultado.getValue()));
  }

}
