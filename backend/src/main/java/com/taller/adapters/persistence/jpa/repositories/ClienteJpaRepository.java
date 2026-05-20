package com.taller.adapters.persistence.jpa.repositories;

import java.util.Optional;

import com.taller.adapters.persistence.jpa.entities.ClienteJpaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, String> {

  Optional<ClienteJpaEntity> findByEmail(String email);
}
