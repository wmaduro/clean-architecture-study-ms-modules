package com.maduro.cas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Mono
import java.time.Duration

@SpringBootApplication
class ApiGatewayApplication 

fun main(args: Array<String>) {
	runApplication<ApiGatewayApplication>(*args)
}
