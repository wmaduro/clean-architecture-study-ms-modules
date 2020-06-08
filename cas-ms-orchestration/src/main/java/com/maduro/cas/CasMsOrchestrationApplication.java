package com.maduro.cas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CasMsOrchestrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasMsOrchestrationApplication.class, args);
	}

}
