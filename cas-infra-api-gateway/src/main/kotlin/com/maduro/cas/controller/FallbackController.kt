package com.maduro.cas.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.maduro.cas.model.GatewayResponse
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/fallback")
class FallbackController {

    private var id: Int = 0

    @PostMapping("/test")
    fun fallback(): Mono<GatewayResponse> = Mono.just(GatewayResponse(++id, "I'm fallback!"))
}