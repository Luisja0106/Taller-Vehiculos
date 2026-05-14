package com.taller.adapters.persistence.jpa.repositories;

import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.ServicioJpaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioJpaRepository extends JpaRepository<ServicioJpaEntity, String> {

  Optional<ServicioJpaEntity> findByNombre(String nombre);
}
