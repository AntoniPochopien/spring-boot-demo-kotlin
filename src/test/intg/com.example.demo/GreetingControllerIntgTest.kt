package com.example.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class GreetingControllerIntegrationTest{
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun retrieveGreeting(){
        val name = "Xyz"
        val result = webTestClient.get().uri("/api/{name}", name).exchange().expectStatus().is2xxSuccessful.expectBody(String::class.java).returnResult()
        Assertions.assertEquals("$name, Hello from default env", result.responseBody)
    }
}