package com.fiap.vehicle.core.infrastructure.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringClientRepository
		extends JpaRepository<ClientEntity, String>, JpaSpecificationExecutor<ClientEntity> {

}
