package com.fiap.vehicle.core.infrastructure.client.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ClientRequestUpdateDTO {

	@Schema(description = "Nome do Cliente", example = "Cliente da Silva")
	@NotNull(message = "O nome do Cliente não pode ser nullo")
	private String nome;

	@Schema(description = "Email do Cliente", example = "cliente@gmail.com")
	private String email;

}
