package com.fiap.vehicle.core.infrastructure.persistence.vehicle;

import br.com.six2six.fixturefactory.Fixture;
import com.fiap.vehicle.core.support.TestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.fiap.vehicle.core.support.FixtureTemplates.VALID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VehicleEntityTest extends TestSupport {

	private VehicleEntity vehicleEntity;

	@Override
	public void init() {
		vehicleEntity = Fixture.from(VehicleEntity.class).gimme(VALID.name());
	}

	@Test
	public void should_return_vehicle_id() {
		Long vehicleId = 10L;
		vehicleEntity.setVehicleId(vehicleId);
		Assertions.assertEquals(vehicleId, vehicleEntity.getVehicleId());
	}

	@Test
	public void should_return_make() {
		String make = "Ford";
		vehicleEntity.setMake(make);
		Assertions.assertEquals(make, vehicleEntity.getMake());
	}

	@Test
	public void should_create_vehicle() {
		VehicleEntity vehicleEntity = new VehicleEntity();
		vehicleEntity.setMake("Ford");
		vehicleEntity.setModel("Fiesta");
		vehicleEntity.setColor("VERMELHO");
		vehicleEntity.setMileage(1000);
		vehicleEntity.setPrice(new BigDecimal(10000));
		vehicleEntity.setStatus("NOVO");
		vehicleEntity.setDataFabricacao(OffsetDateTime.now());
		vehicleEntity.setCreatedAt(OffsetDateTime.now());
		vehicleEntity.setUpdatedAt(OffsetDateTime.now());
		Assertions.assertNotNull(vehicleEntity);
	}

}
