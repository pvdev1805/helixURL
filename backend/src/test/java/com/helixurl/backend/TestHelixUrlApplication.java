package com.helixurl.backend;

import org.springframework.boot.SpringApplication;

public class TestHelixUrlApplication {

	public static void main(String[] args) {
		SpringApplication.from(HelixUrlApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
