package com.hotel.pad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hotel.pad"})
public class HotelAvailibilityPadApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelAvailibilityPadApplication.class, args);
	}

}
