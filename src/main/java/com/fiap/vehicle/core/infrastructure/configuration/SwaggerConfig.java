package com.fiap.vehicle.core.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI springSwagger() {

		Contact contact = new Contact();
		contact.setEmail("e-mail@gmail.com");
		contact.setName("Vehicle API");
		contact.setUrl("https://www.domain.com");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("Vehicle Core FIAP API")
			.version("1.0")
			.contact(contact)
			.description(
					"Esta API para gerenciar os clientes e veículos. Permite o cadastro, listagem, consulta detalhada e atualização de informações de veículos.")
			.termsOfService("https://www.fiap.com/terms")
			.license(mitLicense);

		return new OpenAPI().info(info)
			.addSecurityItem(new SecurityRequirement().addList("BearerAuth")) // Adiciona
																				// o
																				// esquema
																				// de
																				// autenticação
																				// nas
																				// requisições
			.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("BearerAuth",
					new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))); // Configura
																												// o
																												// tipo
																												// do
																												// token
																												// JWT
	}

}
