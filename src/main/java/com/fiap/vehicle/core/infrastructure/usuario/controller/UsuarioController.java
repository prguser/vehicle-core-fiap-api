package com.fiap.vehicle.core.infrastructure.usuario.controller;



import com.fiap.vehicle.core.domain.entity.usuario.model.Cliente;
import com.fiap.vehicle.core.infrastructure.usuario.dto.request.AuthRequestDTO;
import com.fiap.vehicle.core.infrastructure.usuario.dto.response.ClienteResponseDTO;
import com.fiap.vehicle.core.infrastructure.usuario.dto.request.ClienteRequestUpdateDTO;
import com.fiap.vehicle.core.infrastructure.usuario.dto.request.ClienteRequestDTO;
import com.fiap.vehicle.core.usecases.cliente.AuthorizationUseCase;
import com.fiap.vehicle.core.usecases.cliente.ClienteUseCase;
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
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@Tag(name = "cliente", description = "API responsável pelo cadastrado de clientes")
public class UsuarioController {

    private final ClienteUseCase clienteUseCase;
    private final AuthorizationUseCase authorizationUseCase;

    private final ModelMapper modelMapper;

    public UsuarioController(ClienteUseCase clienteUseCase, ModelMapper modelMapper,AuthorizationUseCase authorizationUseCase) {
        this.clienteUseCase = clienteUseCase;
        this.modelMapper = modelMapper;
        this.authorizationUseCase= authorizationUseCase;
    }

    @Operation(summary = "Buscar um Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao buscar o Cliente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar o Cliente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro ao buscar o Cliente",
                    content = @Content)})
    @GetMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDTO buscarCliente(@Schema(description = "Cpf do Cliente") @PathVariable(value = "cpf") @CPF(message = "Documento Invalido") String cpf) {
        return modelMapper.map(clienteUseCase.buscar(cpf), ClienteResponseDTO.class);

    }

    @Operation(summary = "Cadastrar um Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso ao cadastrar o Cliente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o Cliente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro ao cadastrar o Cliente",
                    content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO cadastrarCliente(@Valid @RequestBody ClienteRequestDTO request) {
        Cliente cliente = modelMapper.map(request, Cliente.class);
        return modelMapper.map(clienteUseCase.cadastrar(cliente), ClienteResponseDTO.class);

    }

    @Operation(summary = "Atualizar as informacoes do Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao atualizar o Cliente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o Cliente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro ao atualizar o Cliente",
                    content = @Content)})
    @PutMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDTO atualizarCliente(@PathVariable @Valid @CPF(message = "CPF do Cliente inválido") String cpf, @Valid @RequestBody ClienteRequestUpdateDTO request) {
        Cliente cliente = modelMapper.map(request, Cliente.class);
        return modelMapper.map(clienteUseCase.atualizar(cpf, cliente), ClienteResponseDTO.class);

    }

    @Operation(summary = "Listar todos os Clientes paginados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao listar os Clientes paginados",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao listar os Clientes paginados",
                    content = @Content)})
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public Page<ClienteResponseDTO> listarClientesPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return clienteUseCase.listarPaginado(pageRequest)
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class));
    }


    @Operation(summary = "Autenticar usuário e gerar token de acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content)})
    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> autenticarPaciente(@RequestBody AuthRequestDTO request) {
        Cliente cliente = clienteUseCase.buscar(request.getCpf());
        if (cliente != null) {
            String token = authorizationUseCase.generateToken(request.getCpf());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciais inválidas"));
        }
    }

}