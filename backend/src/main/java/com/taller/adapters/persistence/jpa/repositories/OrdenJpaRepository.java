package com.taller.adapters.persistence.jpa.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.OrdenJpaEntity;
import com.taller.domain.enums.EstadoDelTrabajo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdenJpaRepository extends JpaRepository<OrdenJpaEntity, String> {

  Optional<OrdenJpaEntity> findByMecanicoId(String mecanicoId);

  Optional<OrdenJpaEntity> findByVehiculoPlaca(String placa);

  Optional<OrdenJpaEntity> findByEstado(EstadoDelTrabajo estado);

  @Query("SELECT o FROM OrdenJpaEntity o WHERE " +
      "(:estado IS NULL OR o.estado = :estado) AND " +
      "(:mecanico IS NULL OR o.mecanico.id = :mecanicoId) AND " +
      "(:placa IS NULL OR o.vehiculo.placa = :placa)")
  List<OrdenJpaEntity> findWithFilters(
      @Param("estado") EstadoDelTrabajo estado,
      @Param("mecanicoId") String mecanicoId,
      @Param("placa") String placa);

  // TODO: Implement the rest of the querys for the reports
}
