package com.example.demo.controller

import com.example.demo.dto.InstructorDTO
import com.example.demo.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/instructors")
@Validated
class InstructorController(val instructorService: InstructorService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@RequestBody instructorDto: InstructorDTO) = instructorService.createInstructor(instructorDto)

    @GetMapping
    fun retrieveAllInstructors():List<InstructorDTO> = instructorService.retrieveAllInstructors()
}