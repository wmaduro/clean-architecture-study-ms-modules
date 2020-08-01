package com.maduro.cas;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class CasMsStorageApplication {
	Logger logger = LoggerFactory.getLogger(CasMsStorageApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CasMsStorageApplication.class, args);
	}

	@Scheduled(fixedDelay=5000)
	public void doSomething() {
		logger.info("teste... "+new Date());
		}
}
