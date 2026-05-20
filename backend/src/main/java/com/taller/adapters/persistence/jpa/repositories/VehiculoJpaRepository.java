package com.taller.adapters.persistence.jpa.repositories;

import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.VehiculoJpaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoJpaRepository extends JpaRepository<VehiculoJpaEntity, Long> {

  Optional<VehiculoJpaEntity> findByPlaca(String placa);

  Optional<VehiculoJpaEntity> findByClienteId(String clienteId);

  void deleteByPlaca(String placa);

}
