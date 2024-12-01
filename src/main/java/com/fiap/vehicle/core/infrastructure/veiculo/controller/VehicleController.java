package com.fiap.vehicle.core.infrastructure.veiculo.controller;

import com.fiap.vehicle.core.domain.entity.veiculo.model.Vehicle;

import com.fiap.vehicle.core.infrastructure.veiculo.dto.request.VehicleRequestDTO;
import com.fiap.vehicle.core.infrastructure.veiculo.dto.response.VehicleResponseDTO;
import com.fiap.vehicle.core.usecases.veiculo.VehicleUseCase;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
@Tag(name = "vehicle", description = "API responsável pelo cadastro de veículos")
public class VehicleController {

    private final VehicleUseCase vehicleUseCase;
    private final ModelMapper modelMapper;

    public VehicleController(VehicleUseCase vehicleUseCase, ModelMapper modelMapper) {
        this.vehicleUseCase = vehicleUseCase;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Buscar um Veículo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao buscar o Veículo",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar o Veículo", content = @Content),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponseDTO buscarVehicleById(
            @Schema(description = "Id do Veículo") @PathVariable(value = "id") Long id) {
        Vehicle vehicle = vehicleUseCase.buscarPorId(id);
        return modelMapper.map(vehicle, VehicleResponseDTO.class);
    }

    @Operation(summary = "Cadastrar um Veículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso ao cadastrar o Veículo",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o Veículo", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponseDTO cadastrarVehicle(@RequestBody @Valid VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = new Vehicle(
                vehicleRequestDTO.getMake(),
                vehicleRequestDTO.getModel(),
                vehicleRequestDTO.getColor(),
                vehicleRequestDTO.getMileage(),
                vehicleRequestDTO.getPrice(),
                vehicleRequestDTO.getStatus(),
                vehicleRequestDTO.getDataFabricacao(),
                OffsetDateTime.now()
        );

        Vehicle savedVehicle = vehicleUseCase.cadastrar(vehicle);
        return mapToResponseDTO(savedVehicle);
    }

    @Operation(summary = "Atualizar um Veículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao atualizar o Veículo",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o Veículo", content = @Content),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponseDTO atualizarVehicle(@PathVariable Long id, @RequestBody @Valid VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleUseCase.buscarPorId(id);

        // Atualiza os campos do veículo existente com os dados do DTO
        vehicle.setMake(vehicleRequestDTO.getMake());
        vehicle.setModel(vehicleRequestDTO.getModel());
        vehicle.setColor(vehicleRequestDTO.getColor());
        vehicle.setMileage(vehicleRequestDTO.getMileage());
        vehicle.setPrice(vehicleRequestDTO.getPrice());
        vehicle.setStatus(vehicleRequestDTO.getStatus());
        vehicle.setDataFabricacao(vehicleRequestDTO.getDataFabricacao());

        Vehicle updatedVehicle = vehicleUseCase.atualizar(vehicle);
        return mapToResponseDTO(updatedVehicle);
    }

    @Operation(summary = "Listar todos os Veículos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao listar os Veículos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleResponseDTO.class)) })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponseDTO> listarTodosVehicles() {
        List<Vehicle> vehicles = vehicleUseCase.listarTodos();
        return vehicles.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private VehicleResponseDTO mapToResponseDTO(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleResponseDTO.class);
    }
}
