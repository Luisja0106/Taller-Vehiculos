package com.taller.adapters.persistence.jpa.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.VehiculoJpaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoJpaRepository extends JpaRepository<VehiculoJpaEntity, String> {

  Optional<VehiculoJpaEntity> findByPlaca(String placa);

  List<VehiculoJpaEntity> findByClienteId(String clienteId);

  void deleteByPlaca(String placa);

}
