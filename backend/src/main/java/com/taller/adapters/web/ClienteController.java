package com.taller.adapters.web;

import java.util.Map;

import com.taller.adapters.web.dto.ClienteDTO;
import com.taller.usecases.ActualizarCliente;
import com.taller.usecases.BuscarClientePorId;
import com.taller.usecases.ListarClientes;
import com.taller.usecases.RegistrarCliente;
import com.taller.usecases.dto.ActualizarClienteCMD;
import com.taller.usecases.dto.BuscarClientePorIdCMD;
import com.taller.usecases.dto.CrearClienteCMD;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

  private final RegistrarCliente registrarCliente;
  private final ActualizarCliente actualizarCliente;
  private final ListarClientes listarClientes;
  private final BuscarClientePorId buscarClientePorId;

  public ClienteController(RegistrarCliente registrarCliente, ActualizarCliente actualizarCliente,
      ListarClientes listarClientes, BuscarClientePorId buscarClientePorId) {
    this.registrarCliente = registrarCliente;
    this.actualizarCliente = actualizarCliente;
    this.listarClientes = listarClientes;
    this.buscarClientePorId = buscarClientePorId;
  }

  @GetMapping
  public ResponseEntity<?> listar() {
    var resultado = listarClientes.ejecutar();

    if (!resultado.isSuccess) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    var dtos = resultado.getValue().stream()
        .map(ClienteDTO::from)
        .toList();

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable String id) {
    var input = new BuscarClientePorIdCMD(id);
    var resultado = buscarClientePorId.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.internalServerError()
          .body(Map.of("error", resultado.getError().getMessage()));
    }

    return ResponseEntity.ok(ClienteDTO.from(resultado.getValue()));
  }

  @PostMapping
  public ResponseEntity<?> registrar(@RequestBody CrearClienteCMD input) {
    var resultado = registrarCliente.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.status(201).body(ClienteDTO.from(resultado.getValue()));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody ActualizarClienteCMD input) {
    var resultado = actualizarCliente.ejecutar(input);

    if (!resultado.isSuccess) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", resultado.getError().getMessage()));
    }
    return ResponseEntity.ok(ClienteDTO.from(resultado.getValue()));
  }

}
