package com.taller.adapters.persistence.jpa.implementations;

import java.util.List;
import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.EmpleadoJpaEntity;
import com.taller.adapters.persistence.jpa.entities.OrdenJpaEntity;
import com.taller.adapters.persistence.jpa.entities.ServicioJpaEntity;
import com.taller.adapters.persistence.jpa.entities.VehiculoJpaEntity;
import com.taller.adapters.persistence.jpa.repositories.EmpleadoJpaRepository;
import com.taller.adapters.persistence.jpa.repositories.OrdenJpaRepository;
import com.taller.adapters.persistence.jpa.repositories.VehiculoJpaRepository;
import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.interfaces.IErrorApp;
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
    var resu = jpaRepo.servicioMasPedidos();

    return resu.stream()
        .map(fila -> {
          ServicioJpaEntity jpaEntity = (ServicioJpaEntity) fila[0];
          long cantidad = (long) fila[1];

          var servicioDominio = jpaEntity.toDomain();

          return new EntidadConteo(servicioDominio.getId(), servicioDominio.getNombreDelServicio(), cantidad);
        })
        .toList();
  }

  @Override
  public List<EntidadConteo> mecanicoConMasServicio() {
    var resu = jpaRepo.mecanicoConMasOrdenes();

    return resu.stream()
        .map(fila -> {
          EmpleadoJpaEntity jpaEntity = (EmpleadoJpaEntity) fila[0];
          long cantidad = (long) fila[1];

          var empleadoDomain = jpaEntity.toDomain();

          return new EntidadConteo(empleadoDomain.getId(), empleadoDomain.getNombre(), cantidad);
        })
        .toList();
  }

  @Override
  public List<EntidadConteo> vehiculosPorServicio() {
    var resu = jpaRepo.vehiculoConMasOrdenes();

    return resu.stream()
        .map(fila -> {
          VehiculoJpaEntity jpaEntity = (VehiculoJpaEntity) fila[0];
          long cantidad = (long) fila[1];

          var vehiculoDomain = jpaEntity.toDomain();

          return new EntidadConteo(vehiculoDomain.getPlaca().getValue(),
              vehiculoDomain.getMarca().toString() + " " + vehiculoDomain.getModelo() + " " + vehiculoDomain.getAnio(),
              cantidad);
        })
        .toList();
  }

  @Override
  public int siguienteNumeroParaId() {
    return (int) jpaRepo.count() + 1;
  }

  @Override
  public List<OrdenDeTrabajo> obtenerOrdenesPorServicioId(String servicioId) {
    List<OrdenJpaEntity> entidades = jpaRepo.findOrdenesByServicioId(servicioId);

    return entidades.stream()
        .map(OrdenJpaEntity::toDomain)
        .toList();
  }

}
