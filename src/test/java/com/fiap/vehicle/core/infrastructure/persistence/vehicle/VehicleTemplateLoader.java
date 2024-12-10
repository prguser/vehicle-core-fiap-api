package com.fiap.vehicle.core.infrastructure.persistence.vehicle;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.fiap.vehicle.core.support.FixtureTemplates.VALID;

public class VehicleTemplateLoader implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(VehicleEntity.class).addTemplate(VALID.name(), new Rule() {
			{
				add("vehicleId", "1");
				add("make", "Ford");
				add("model", "Fiesta");
				add("mileage", 450);
				add("color", "Black");
				add("price", "50000");
				add("status", "NOVO");
			}
		});
	}

}
