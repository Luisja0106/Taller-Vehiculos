package com.taller.adapters.persistence.jpa.implementations;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.taller.adapters.persistence.jpa.entities.EmpleadoJpaEntity;
import com.taller.adapters.persistence.jpa.entities.OrdenJpaEntity;
import com.taller.adapters.persistence.jpa.entities.VehiculoJpaEntity;
import com.taller.adapters.persistence.jpa.repositories.EmpleadoJpaRepository;
import com.taller.adapters.persistence.jpa.repositories.OrdenJpaRepository;
import com.taller.adapters.persistence.jpa.repositories.VehiculoJpaRepository;
import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.interfaces.IServicio;
import com.taller.domain.repositories.IOrdenRepository;
import com.taller.domain.utils.Result;
import com.taller.usecases.output.EntidadConteo;

import org.springframework.stereotype.Repository;

@Repository
public class OrdenRepositoryImpl implements IOrdenRepository {

  private final OrdenJpaRepository jpaRepo;
  private final VehiculoJpaRepository vehiculoJpaRepo;
  private final EmpleadoJpaRepository empleadoJpaRepo;

  public OrdenRepositoryImpl(OrdenJpaRepository jpaRepo, VehiculoJpaRepository vehiculoJpaRepo,
      EmpleadoJpaRepository empleadoJpaRepo) {
    this.jpaRepo = jpaRepo;
    this.vehiculoJpaRepo = vehiculoJpaRepo;
    this.empleadoJpaRepo = empleadoJpaRepo;
  }

  @Override
  public Result<OrdenDeTrabajo, IErrorApp> guardar(OrdenDeTrabajo orden) {
    VehiculoJpaEntity vehiculoEntity = vehiculoJpaRepo
        .findByPlaca(orden.getVehiculo().getPlaca().toString())
        .orElseThrow(() -> new IllegalStateException("Vehiculo no encontrado en DB"));

    EmpleadoJpaEntity empleadoEntity = empleadoJpaRepo
        .findById(orden.getEmpleadoACargo().getId())
        .orElseThrow(() -> new IllegalStateException("Empleado no encontrado en DB"));

    OrdenJpaEntity entity = new OrdenJpaEntity(orden, vehiculoEntity, empleadoEntity);
    jpaRepo.save(entity);
    return Result.success(orden);
  }

  @Override
  public Result<OrdenDeTrabajo, IErrorApp> actualizar(OrdenDeTrabajo orden) {
    OrdenJpaEntity entity = new OrdenJpaEntity(orden);
    jpaRepo.save(entity);
    return Result.success(orden);
  }

  @Override
  public Optional<OrdenDeTrabajo> buscarPorId(String id) {
    return jpaRepo.findById(id)
        .map(OrdenJpaEntity::toDomain);
  }

  @Override
  public List<OrdenDeTrabajo> listarTodos() {
    return jpaRepo.findAll().stream()
        .map(OrdenJpaEntity::toDomain)
        .toList();
  }

  @Override
  public List<OrdenDeTrabajo> listarConFiltros(EstadoDelTrabajo estado, String empleadoId, String placaVehiculo) {
    return jpaRepo.findWithFilters(estado, empleadoId, placaVehiculo).stream()
        .map(OrdenJpaEntity::toDomain)
        .toList();
  }

  @Override
  public void eliminar(String id) {
    jpaRepo.deleteById(id);
  }

  @Override
  public List<EntidadConteo> serviciosMasPedidos() {
    // FIXME: correct this method
    return jpaRepo.findAll().stream() // same that listar filtros, don't relly think that the backend should do this
        .map(OrdenJpaEntity::toDomain)
        .flatMap(o -> o.getServicios().stream())
        .collect(Collectors.groupingBy(
            IServicio::getId))
        .entrySet().stream()
        .map(e -> new EntidadConteo(e.getKey(), e.getValue().get(0).getNombreDelServicio(), e.getValue().size()))
        .sorted(Comparator.comparingInt(EntidadConteo::cantidad).reversed())
        .toList();

  }

  @Override
  public List<EntidadConteo> mecanicoConMasServicio() {
    // TODO: correct this method
    throw new UnsupportedOperationException("Unimplemented method 'mecanicoConMasServicio'");
  }

  @Override
  public List<EntidadConteo> vehiculosPorServicio() {
    // TODO: correct this method
    throw new UnsupportedOperationException("Unimplemented method 'vehiculosPorServicio'");
  }

  @Override
  public int siguienteNumeroParaId() {
    return (int) jpaRepo.count() + 1;
  }

}
