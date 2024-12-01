package com.fiap.vehicle.core.domain.entity.usuario.gateway;

import com.fiap.vehicle.core.domain.entity.usuario.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ClienteGateway {

    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarClientePorCPF(String cpf);
    Page<Cliente> listarPaginado(Pageable pageable);


}
