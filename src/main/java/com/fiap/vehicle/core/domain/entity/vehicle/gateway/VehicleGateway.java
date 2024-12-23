package com.fiap.vehicle.core.domain.entity.vehicle.gateway;

import com.fiap.vehicle.core.domain.entity.vehicle.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VehicleGateway {

	Vehicle salvar(Vehicle vehicle);

	Optional<Vehicle> buscarVeiculoPorId(Long vehicleId);

	Page<Vehicle> listarPaginado(Pageable pageable);

	List<Vehicle> buscarTodos();

}
