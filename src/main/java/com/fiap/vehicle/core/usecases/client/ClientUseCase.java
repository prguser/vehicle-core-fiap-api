package com.fiap.vehicle.core.usecases.client;

import com.fiap.vehicle.core.domain.entity.client.gateway.ClientGateway;
import com.fiap.vehicle.core.domain.entity.client.model.Client;
import com.fiap.vehicle.core.domain.entity.client.validator.CpfValidator;
import com.fiap.vehicle.core.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ClientUseCase {

	final private ClientGateway clientGateway;

	public ClientUseCase(ClientGateway clientGateway) {
		this.clientGateway = clientGateway;
	}

	public Client buscar(String cpf) {
		return clientGateway.buscarClientePorCPF(CpfValidator.sanitizar(cpf))
			.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado para o cpf: " + cpf));
	}

	public Client cadastrar(Client client) {
		client.setCpf(CpfValidator.sanitizar(client.getCpf()));
		client.validarCadastro();
		var clientEntity = clientGateway.salvar(client);
		return clientEntity;
	}

	public Client atualizar(String cpf, Client request) {
		var cliente = clientGateway.buscarClientePorCPF(CpfValidator.sanitizar(cpf))
			.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado para o cpf: " + cpf));
		cliente.atualizar(request.getNome(), request.getEmail());
		return clientGateway.salvar(cliente);
	}

	public Page<Client> listarPaginado(Pageable pageable) {
		return clientGateway.listarPaginado(pageable);
	}

}
