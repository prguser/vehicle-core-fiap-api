package com.fiap.vehicle.core.infrastructure.client.gateway;

import com.fiap.vehicle.core.domain.entity.client.gateway.ClientGateway;
import com.fiap.vehicle.core.domain.entity.client.model.Client;
import com.fiap.vehicle.core.infrastructure.persistence.client.ClientEntity;
import com.fiap.vehicle.core.infrastructure.persistence.client.SpringClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class ClientDataBaseRepository implements ClientGateway {

	private final SpringClientRepository clienteRepository;

	private final ModelMapper modelMapper;

	public ClientDataBaseRepository(SpringClientRepository clienteRepository, ModelMapper modelMapper) {
		this.clienteRepository = clienteRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional()
	public Client salvar(Client client) {
		var clienteEntity = clienteRepository.saveAndFlush(modelMapper.map(client, ClientEntity.class));
		return modelMapper.map(clienteEntity, Client.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Client> buscarClientePorCPF(String cpf) {
		var clienteEntity = clienteRepository.findById(cpf);
		return clienteEntity.map(entity -> modelMapper.map(entity, Client.class));

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Client> listarPaginado(Pageable pageable) {
		Page<ClientEntity> clienteEntitiesPage = clienteRepository.findAll(pageable);
		return clienteEntitiesPage.map(entity -> modelMapper.map(entity, Client.class));
	}

}
