package com.fiap.vehicle.core.infrastructure.configuration;

import com.fiap.vehicle.core.domain.entity.veiculo.gateway.VehicleGateway;
import com.fiap.vehicle.core.domain.entity.usuario.gateway.ClienteGateway;


import com.fiap.vehicle.core.infrastructure.persistence.veiculo.VehicleRepository;
import com.fiap.vehicle.core.infrastructure.persistence.cliente.SpringClienteRepository;
import com.fiap.vehicle.core.infrastructure.sales.VehicleSalesClient;
import com.fiap.vehicle.core.infrastructure.usuario.gateway.ClienteDataBaseRepository;
import com.fiap.vehicle.core.infrastructure.veiculo.gateway.VeiculoDataBaseRepository;
import com.fiap.vehicle.core.usecases.cliente.AuthorizationUseCase;
import com.fiap.vehicle.core.usecases.sales.VehicleCoreService;
import com.fiap.vehicle.core.usecases.veiculo.VehicleUseCase;
import com.fiap.vehicle.core.usecases.cliente.ClienteUseCase;
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
		return new VeiculoDataBaseRepository(vehicleRepository, mapper);
	}

	@Bean
	public VehicleUseCase createVehicleUseCase(VehicleGateway vehicleGateway, VehicleCoreService vehicleSalesClient) {
		return new VehicleUseCase(vehicleGateway, vehicleSalesClient);
	}

	@Bean
	public ClienteGateway createClienteGateway(SpringClienteRepository springClienteRepository,ModelMapper mapper) {
		return new ClienteDataBaseRepository(springClienteRepository,mapper);
	}

	@Bean
	ClienteUseCase createUserCase(ClienteGateway clienteGateway) {
		return new ClienteUseCase(clienteGateway);
	}

	@Bean
	AuthorizationUseCase createAuthorizationUseCase() {
		return new AuthorizationUseCase();
	}

}
