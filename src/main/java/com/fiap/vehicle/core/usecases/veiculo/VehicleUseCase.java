package com.fiap.vehicle.core.usecases.veiculo;

import com.fiap.vehicle.core.domain.entity.veiculo.gateway.VehicleGateway;
import com.fiap.vehicle.core.domain.entity.veiculo.model.Vehicle;
import com.fiap.vehicle.core.infrastructure.sales.VehicleSalesClient;
import com.fiap.vehicle.core.usecases.sales.VehicleCoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleUseCase {

    private final VehicleCoreService vehicleSalesClient;
    private final VehicleGateway vehicleGateway;

    // Construtor ajustado para injetar ambas as dependências
    public VehicleUseCase(VehicleGateway vehicleGateway, VehicleCoreService vehicleSalesClient) {
        this.vehicleGateway = vehicleGateway;
        this.vehicleSalesClient = vehicleSalesClient;
    }

    public Vehicle cadastrar(Vehicle vehicle) {
        // Salva o veículo no gateway
        Vehicle v = vehicleGateway.salvar(vehicle);
        vehicleSalesClient.cadastrarVehicleParaVenda(v);

        return v;
    }

    public Vehicle buscarPorId(Long id) {
        return vehicleGateway.buscarVeiculoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));
    }

    public Vehicle atualizar(Vehicle vehicle) {
        return vehicleGateway.salvar(vehicle);
    }

    public List<Vehicle> listarTodos() {
        return vehicleGateway.buscarTodos();
    }
}
