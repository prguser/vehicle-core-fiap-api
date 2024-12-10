package com.fiap.vehicle.core.usecases.sales;

import com.fiap.vehicle.core.domain.entity.vehicle.model.Vehicle;
import com.fiap.vehicle.core.infrastructure.sales.VehicleSalesClient;
import org.springframework.stereotype.Service;

@Service
public class VehicleCoreService {

	private final VehicleSalesClient vehicleSalesClient;

	public VehicleCoreService(VehicleSalesClient vehicleSalesClient) {
		this.vehicleSalesClient = vehicleSalesClient;
	}

	public Vehicle cadastrarVehicleParaVenda(Vehicle vehicle) {
		return vehicleSalesClient.cadastrarVehicle(vehicle);
	}

}
