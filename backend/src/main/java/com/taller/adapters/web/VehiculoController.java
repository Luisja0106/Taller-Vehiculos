package com.taller.adapters.web;

import java.util.Map;

import com.taller.adapters.web.dto.VehiculoDTO;
import com.taller.usecases.ActualizarVehiculo;
import com.taller.usecases.BuscarVehiculoPorPlaca;
import com.taller.usecases.ListarVehiculos;
import com.taller.usecases.RegistrarVehiculo;
import com.taller.usecases.RemoveVehiculo;
import com.taller.usecases.dto.ActualizarVehiculoCMD;
import com.taller.usecases.dto.BuscarVehiculoPorPlacaCMD;
import com.taller.usecases.dto.CrearVehiculoCMD;
import com.taller.usecases.dto.ListarVehiculosCMD;

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
@RequestMapping("/api/vehiculos")
public class VehiculoController {

  private final RegistrarVehiculo registrarVehiculo;
  private final ActualizarVehiculo actualizarVehiculo;
  private final ListarVehiculos listarVehiculos;
  private final BuscarVehiculoPorPlaca buscarVehiculoPorPlaca;
  private final RemoveVehiculo removeVehiculo;

  public VehiculoController(RegistrarVehiculo registrarVehiculo, ActualizarVehiculo actualizarVehiculo,
      ListarVehiculos listarVehiculos, BuscarVehiculoPorPlaca buscarVehiculoPorPlaca, RemoveVehiculo removeVehiculo) {
    this.registrarVehiculo = registrarVehiculo;
    this.actualizarVehiculo = actualizarVehiculo;
    this.listarVehiculos = listarVehiculos;
    this.buscarVehiculoPorPlaca = buscarVehiculoPorPlaca;
    this.removeVehiculo = removeVehiculo;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String clienteId) {
    var input = new ListarVehiculosCMD(clienteId);
    var resultado = listarVehiculos.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.status(404)
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    var dtos = resultado.getValue().stream()
        .map(VehiculoDTO::from)
        .toList();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{placa}")
  public ResponseEntity<?> buscarPorPlaca(@PathVariable String placa) {
    var input = new BuscarVehiculoPorPlacaCMD(placa);
    var resultado = buscarVehiculoPorPlaca.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.status(404)
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    return ResponseEntity.ok(VehiculoDTO.from(resultado.getValue()));
  }

  @PostMapping
  public ResponseEntity<?> registrar(@RequestBody CrearVehiculoCMD input) {
    var resultado = registrarVehiculo.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(201).body(VehiculoDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{placa}")
  public ResponseEntity<?> actualizar(@PathVariable String placa, @RequestBody ActualizarVehiculoCMD input) {
    var resultado = actualizarVehiculo.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(VehiculoDTO.from(resultado.getValue()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    var resultado = removeVehiculo.ejecutar(id);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(204).build();
  }
}
