package com.taller.adapters.persistence.jpa.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.EmpleadoJpaEntity;
import com.taller.domain.enums.Rol;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoJpaRepository extends JpaRepository<EmpleadoJpaEntity, String> {

  Optional<EmpleadoJpaEntity> findByEmail(String email);

  List<EmpleadoJpaEntity> findByRol(Rol rol);
}
