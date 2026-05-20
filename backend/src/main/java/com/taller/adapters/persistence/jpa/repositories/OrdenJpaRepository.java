package com.taller.adapters.persistence.jpa.repositories;

import java.util.List;

import com.taller.adapters.persistence.jpa.entities.OrdenJpaEntity;
import com.taller.domain.enums.EstadoDelTrabajo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdenJpaRepository extends JpaRepository<OrdenJpaEntity, String> {

  List<OrdenJpaEntity> findByMecanicoId(String mecanicoId);

  List<OrdenJpaEntity> findByVehiculoPlaca(String placa);

  List<OrdenJpaEntity> findByEstado(EstadoDelTrabajo estado);

  @Query("SELECT o FROM OrdenJpaEntity o WHERE " +
      "(:estado IS NULL OR o.estado = :estado) AND " +
      "(:mecanicoId IS NULL OR o.mecanico.id = :mecanicoId) AND " +
      "(:placa IS NULL OR o.vehiculo.placa = :placa)")
  List<OrdenJpaEntity> findWithFilters(
      @Param("estado") EstadoDelTrabajo estado,
      @Param("mecanicoId") String mecanicoId,
      @Param("placa") String placa);

  // TODO: Implement the rest of the querys for the reports
}
