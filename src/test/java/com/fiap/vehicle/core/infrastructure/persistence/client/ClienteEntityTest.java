package com.fiap.vehicle.core.infrastructure.persistence.client;

import br.com.six2six.fixturefactory.Fixture;
import com.fiap.vehicle.core.CpfGenerator;
import com.fiap.vehicle.core.support.TestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static com.fiap.vehicle.core.support.FixtureTemplates.VALID;

public class ClienteEntityTest extends TestSupport {

	private ClientEntity clientEntity;

	@Override
	public void init() {
		clientEntity = Fixture.from(ClientEntity.class).gimme(VALID.name());
	}

	@Test
	public void should_return_client_id() {
		String cpf = CpfGenerator.generateCPF();
		clientEntity.setCpf(cpf);
		Assertions.assertEquals(cpf, clientEntity.getCpf());
	}

	@Test
	public void should_create_client() {
		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setCpf(CpfGenerator.generateCPF());
		clientEntity.setNome("João");
		clientEntity.setEmail("teste@mail.com");
		clientEntity.setDatacadastro(OffsetDateTime.now());
		Assertions.assertNotNull(clientEntity);
	}

}
