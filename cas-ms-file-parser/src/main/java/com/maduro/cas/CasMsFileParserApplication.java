package com.maduro.cas;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class CasMsFileParserApplication {


	public static void main(String[] args) {
		SpringApplication.run(CasMsFileParserApplication.class, args);
	}

//	@Bean
//	ApplicationRunner applicationRunner(Environment environment) {
//		return args -> {
//			System.out.println("3--> " + environment.getProperty("cas.ms.storage.port", "liooooooooo"));
//		};
//	}
	
}
