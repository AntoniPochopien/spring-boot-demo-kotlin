package com.example.demo

import com.example.demo.service.GreetingService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}


@RestController
@RequestMapping("/api")
class GreetingController(val greetingService: GreetingService) {
    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name: String): String{
        logger.info { "Name is $name" }
        return greetingService.retrieveGreeting(name)
    }
}