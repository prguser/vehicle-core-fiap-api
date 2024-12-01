package com.fiap.vehicle.core.infrastructure.persistence.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringClienteRepository  extends JpaRepository<ClienteEntity, String>, JpaSpecificationExecutor<ClienteEntity> {
}

