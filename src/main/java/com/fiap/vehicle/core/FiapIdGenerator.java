package com.fiap.vehicle.core;

import java.util.Random;
import java.util.UUID;

public class FiapIdGenerator {

	public static String generateId() {
		return UUID.randomUUID().toString();
	}

	public static long generateIdLong() {
		Random random = new Random();
		return random.nextLong();
	}

}
