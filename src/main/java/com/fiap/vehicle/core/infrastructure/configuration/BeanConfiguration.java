package com.fiap.vehicle.core.infrastructure.configuration;

import com.fiap.vehicle.core.domain.entity.vehicle.gateway.VehicleGateway;
import com.fiap.vehicle.core.domain.entity.client.gateway.ClientGateway;

import com.fiap.vehicle.core.infrastructure.persistence.vehicle.VehicleRepository;
import com.fiap.vehicle.core.infrastructure.persistence.client.SpringClientRepository;
import com.fiap.vehicle.core.infrastructure.client.gateway.ClientDataBaseRepository;
import com.fiap.vehicle.core.infrastructure.vehicle.gateway.VehicleDataBaseRepository;
import com.fiap.vehicle.core.usecases.client.AuthorizationUseCase;
import com.fiap.vehicle.core.usecases.sales.VehicleCoreService;
import com.fiap.vehicle.core.usecases.vehicle.VehicleUseCase;
import com.fiap.vehicle.core.usecases.client.ClientUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public VehicleGateway createVehicleGateway(VehicleRepository vehicleRepository, ModelMapper mapper) {
		return new VehicleDataBaseRepository(vehicleRepository, mapper);
	}

	@Bean
	public VehicleUseCase createVehicleUseCase(VehicleGateway vehicleGateway, VehicleCoreService vehicleSalesClient) {
		return new VehicleUseCase(vehicleGateway, vehicleSalesClient);
	}

	@Bean
	public ClientGateway createClienteGateway(SpringClientRepository springClientRepository, ModelMapper mapper) {
		return new ClientDataBaseRepository(springClientRepository, mapper);
	}

	@Bean
	ClientUseCase createUserCase(ClientGateway clientGateway) {
		return new ClientUseCase(clientGateway);
	}

	@Bean
	AuthorizationUseCase createAuthorizationUseCase() {
		return new AuthorizationUseCase();
	}

}
