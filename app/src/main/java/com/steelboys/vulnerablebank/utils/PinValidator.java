package com.steelboys.vulnerablebank.utils;

import java.util.Random;

public class PinValidator {
	public static boolean validatePin(final Integer pin) {
		Random random = new Random();

		// Generate a 6-digit random number
		int randomNumber = 100000 + random.nextInt(900000);

		return pin == randomNumber;
	}
}
