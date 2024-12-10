package com.fiap.vehicle.core.infrastructure.sales;

import com.fiap.vehicle.core.domain.entity.vehicle.model.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "vehicle-sales-api", url = "${vehicle-sales-api.url}")
public interface VehicleSalesClient {

	@PostMapping("/sales/vehicles")
	Vehicle cadastrarVehicle(@RequestBody Vehicle vehicleRequest);

}
