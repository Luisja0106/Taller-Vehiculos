package com.taller.usecases.fakes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.interfaces.IServicio;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.output.EntidadConteo;

public class OrdenRepositoryFake implements IOrdenRepository {

  private final List<OrdenDeTrabajo> ordenes = new ArrayList<>();
  private int contador = 0;

  @Override
  public Result<OrdenDeTrabajo, IErrorApp> guardar(OrdenDeTrabajo orden) {
    ordenes.add(orden);
    return Result.success(orden);
  }

  @Override
  public Result<OrdenDeTrabajo, IErrorApp> actualizar(OrdenDeTrabajo orden) {
    ordenes.removeIf(o -> o.getID().equals(orden.getID()));
    ordenes.add(orden);
    return Result.success(orden);
  }

  @Override
  public Optional<OrdenDeTrabajo> buscarPorId(String id) {
    return ordenes.stream()
        .filter(o -> o.getID().equals(id))
        .findFirst();
  }

  @Override
  public List<OrdenDeTrabajo> listarTodos() {
    return new ArrayList<>(ordenes);
  }

  @Override
  public List<OrdenDeTrabajo> listarConFiltros(EstadoDelTrabajo estado, String empleadoId, String placaVehiculo) {
    return ordenes.stream()
        .filter(o -> estado == null || o.getEstado().equals(estado))
        .filter(o -> empleadoId == null || o.getEmpleadoACargo().getId().equals(empleadoId))
        .filter(o -> placaVehiculo == null || o.getVehiculo().getPlaca().getValue().equals(placaVehiculo))
        .toList();
  }

  @Override
  public void eliminar(String id) {
    ordenes.removeIf(o -> o.getID().equals(id));
  }

  @Override
  public List<EntidadConteo> serviciosMasPedidos() {
    return ordenes.stream() // create a stream
        .flatMap(o -> o.getServicios().stream()) // flattens all service from all orders into a stream ["Cambio de
                                                 // bujia", "Cambio de aceite", "Cambio de aceite"]
        .collect(Collectors.groupingBy(
            IServicio::getId))// group by id of the service
        .entrySet().stream()
        .map(e -> new EntidadConteo(e.getKey(), // create the record, key is the id
            e.getValue().get(0).getNombreDelServicio(), // nombre del servicio
            e.getValue().size())) // int quantity
        .sorted(Comparator.comparingLong(EntidadConteo::cantidad).reversed()) // sort the info using the quantity
        .toList(); // create a list with the info
  }

  @Override
  public List<EntidadConteo> mecanicoConMasServicio() {
    return ordenes.stream()
        .collect(Collectors.groupingBy(
            o -> o.getEmpleadoACargo().getId()))
        .entrySet().stream()
        .map(e -> {
          String nombre = e.getValue().get(0).getEmpleadoACargo().getNombre();
          return new EntidadConteo(e.getKey(), nombre, e.getValue().size());
        })
        .sorted(Comparator.comparingLong(EntidadConteo::cantidad).reversed())
        .toList();
  }

  @Override
  public List<EntidadConteo> vehiculosPorServicio() {
    return ordenes.stream()
        .flatMap(o -> o.getServicios().stream())
        .collect(Collectors.groupingBy(
            IServicio::getId))
        .entrySet().stream()
        .map(e -> new EntidadConteo(e.getKey(),
            e.getValue().get(0).getNombreDelServicio(),
            e.getValue().size()))
        .sorted(Comparator.comparingLong(EntidadConteo::cantidad).reversed())
        .toList();
  }

  @Override
  public int siguienteNumeroParaId() {
    return ++contador;
  }

  @Override
  public List<OrdenDeTrabajo> obtenerOrdenesPorServicioId(String servicioId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'obtenerOrdenesPorServicioId'");
  }

  @Override
  public List<OrdenDeTrabajo> listarOrdenesActivas(String empleadoId, String placaVehiculo) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listarOrdenesActivas'");
  }

}
