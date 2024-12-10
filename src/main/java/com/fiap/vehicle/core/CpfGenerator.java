package com.fiap.vehicle.core;

import java.util.Random;

public class CpfGenerator {

	public static String generateCPF() {
		Random random = new Random();

		// Generate the first 9 digits
		int[] cpf = new int[11];
		for (int i = 0; i < 9; i++) {
			cpf[i] = random.nextInt(10);
		}

		// Calculate the 10th digit (first check digit)
		cpf[9] = calculateCheckDigit(cpf, 10);

		// Calculate the 11th digit (second check digit)
		cpf[10] = calculateCheckDigit(cpf, 11);

		// Convert the array to a string
		StringBuilder cpfString = new StringBuilder();
		for (int digit : cpf) {
			cpfString.append(digit);
		}
		return cpfString.toString();
	}

	private static int calculateCheckDigit(int[] cpf, int factor) {
		int sum = 0;
		for (int i = 0; i < factor - 1; i++) {
			sum += cpf[i] * (factor - i);
		}
		int remainder = sum % 11;
		return (remainder < 2) ? 0 : 11 - remainder;
	}

	public static void main(String[] args) {
		// Test the generator
		System.out.println("Random CPF: " + generateCPF());
	}

}
