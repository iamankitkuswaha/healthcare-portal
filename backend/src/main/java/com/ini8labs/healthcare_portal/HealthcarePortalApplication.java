package com.ini8labs.healthcare_portal;

import com.ini8labs.healthcare_portal.config.FileStorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class HealthcarePortalApplication {
	private static final Logger logger = LoggerFactory.getLogger(HealthcarePortalApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(HealthcarePortalApplication.class, args);
	}
	@Bean
	CommandLineRunner init(FileStorageProperties props) {
		return args -> {
			Path dir = Paths.get(props.getUploadDir()).toAbsolutePath().normalize();

			if (!Files.exists(dir)) {
				Files.createDirectories(dir);
				logger.info("Created uploads directory: {}");
			} else {
				logger.info("Uploads directory already exists: {}");
			}
		};
	}
}
