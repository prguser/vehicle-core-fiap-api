package com.fiap.vehicle.core.infrastructure.persistence.client;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static com.fiap.vehicle.core.support.FixtureTemplates.VALID;

public class ClientTemplateLoader implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(ClientEntity.class).addTemplate(VALID.name(), new Rule() {
			{
				add("nome", "John Doe");
				add("cpf", "12345678901");
				add("email", "email@mail.com");

			}
		});
	}

}
