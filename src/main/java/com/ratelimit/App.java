package com.ratelimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Description;

/**
 * API Key Rate Limit
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.ratelimit")
@Description("API Key Rate Limit")
public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		LOGGER.info("STARTED!");
		SpringApplication.run(App.class, args);
	}
}
