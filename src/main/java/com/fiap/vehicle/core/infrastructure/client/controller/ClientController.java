package com.fiap.vehicle.core.infrastructure.client.controller;

import com.fiap.vehicle.core.domain.entity.client.model.Client;
import com.fiap.vehicle.core.infrastructure.client.dto.request.AuthRequestDTO;
import com.fiap.vehicle.core.infrastructure.client.dto.response.ClientResponseDTO;
import com.fiap.vehicle.core.infrastructure.client.dto.request.ClientRequestUpdateDTO;
import com.fiap.vehicle.core.infrastructure.client.dto.request.ClientRequestDTO;
import com.fiap.vehicle.core.usecases.client.AuthorizationUseCase;
import com.fiap.vehicle.core.usecases.client.ClientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/client")
@Tag(name = "client", description = "API responsável pelo cadastrado de clientes")
public class ClientController {

	private final ClientUseCase clientUseCase;

	private final AuthorizationUseCase authorizationUseCase;

	private final ModelMapper modelMapper;

	public ClientController(ClientUseCase clientUseCase, ModelMapper modelMapper,
			AuthorizationUseCase authorizationUseCase) {
		this.clientUseCase = clientUseCase;
		this.modelMapper = modelMapper;
		this.authorizationUseCase = authorizationUseCase;
	}

	@Operation(summary = "Buscar um Cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso ao buscar o Cliente",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ClientResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro ao buscar o Cliente", content = @Content),
			@ApiResponse(responseCode = "404", description = "Erro ao buscar o Cliente", content = @Content) })
	@GetMapping("/{cpf}")
	@ResponseStatus(HttpStatus.OK)
	public ClientResponseDTO buscarCliente(@Schema(description = "Cpf do Cliente") @PathVariable(value = "cpf") @CPF(
			message = "Documento Invalido") String cpf) {
		return modelMapper.map(clientUseCase.buscar(cpf), ClientResponseDTO.class);

	}

	@Operation(summary = "Cadastrar um Cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sucesso ao cadastrar o Cliente",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ClientResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro ao cadastrar o Cliente", content = @Content),
			@ApiResponse(responseCode = "404", description = "Erro ao cadastrar o Cliente", content = @Content) })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClientResponseDTO cadastrarCliente(@Valid @RequestBody ClientRequestDTO request) {
		Client client = modelMapper.map(request, Client.class);
		return modelMapper.map(clientUseCase.cadastrar(client), ClientResponseDTO.class);

	}

	@Operation(summary = "Atualizar as informacoes do Cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso ao atualizar o Cliente",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ClientResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro ao atualizar o Cliente", content = @Content),
			@ApiResponse(responseCode = "404", description = "Erro ao atualizar o Cliente", content = @Content) })
	@PutMapping("/{cpf}")
	@ResponseStatus(HttpStatus.OK)
	public ClientResponseDTO atualizarCliente(@PathVariable @Valid @CPF(message = "CPF do Cliente inválido") String cpf,
			@Valid @RequestBody ClientRequestUpdateDTO request) {
		Client client = modelMapper.map(request, Client.class);
		return modelMapper.map(clientUseCase.atualizar(cpf, client), ClientResponseDTO.class);

	}

	@Operation(summary = "Listar todos os Clientes paginados")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Sucesso ao listar os Clientes paginados",
							content = { @Content(mediaType = "application/json",
									schema = @Schema(implementation = Page.class)) }),
					@ApiResponse(responseCode = "400", description = "Erro ao listar os Clientes paginados",
							content = @Content) })
	@GetMapping("/listar")
	@ResponseStatus(HttpStatus.OK)
	public Page<ClientResponseDTO> listarClientesPaginados(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return clientUseCase.listarPaginado(pageRequest)
			.map(client -> modelMapper.map(client, ClientResponseDTO.class));
	}

	@Operation(summary = "Autenticar usuário e gerar token de acesso")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
					content = { @Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content) })
	@PostMapping("/auth")
	public ResponseEntity<Map<String, String>> autenticarPaciente(@RequestBody AuthRequestDTO request) {
		Client client = clientUseCase.buscar(request.getCpf());
		if (client != null) {
			String token = authorizationUseCase.generateToken(request.getCpf());

			Map<String, String> response = new HashMap<>();
			response.put("token", token);

			return ResponseEntity.ok(response);
		}
		else {
			return ResponseEntity.status(401).body(Map.of("message", "Credenciais inválidas"));
		}
	}

}