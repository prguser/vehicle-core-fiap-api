package com.fiap.vehicle.core.domain.entity.client.gateway;

import com.fiap.vehicle.core.domain.entity.client.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClientGateway {

	Client salvar(Client client);

	Optional<Client> buscarClientePorCPF(String cpf);

	Page<Client> listarPaginado(Pageable pageable);

}
