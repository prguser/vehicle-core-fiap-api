package com.fiap.vehicle.core.infrastructure.persistence.veiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
