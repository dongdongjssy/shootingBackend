package io.goose.cloud.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableConfigServer
public class GooseRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GooseRegistryApplication.class, args);
		System.out.println("registry and config center start successfully");
	}
}
