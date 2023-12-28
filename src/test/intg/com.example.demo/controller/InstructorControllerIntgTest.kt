package com.example.demo.controller

import com.example.demo.dto.CourseDTO
import com.example.demo.dto.InstructorDTO
import com.example.demo.repository.ICourseRepository
import com.example.demo.repository.IInstructorRepository
import com.example.demo.utils.courseEntityList
import com.example.demo.utils.instructorEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorControllerIntgTest {


    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var instructorRepository: IInstructorRepository

    @BeforeEach
    fun setUp() {
        instructorRepository.deleteAll()
        val instructors = instructorEntityList()
        instructorRepository.saveAll(instructors)
    }

    @Test
    fun addInstructor(){
        val instructorDTO = InstructorDTO(null, "awesome instructor", courses = emptyList())
        val savedInstructorDTO = webTestClient.post().uri("api/instructors").bodyValue(instructorDTO).exchange()
                .expectStatus().isCreated.expectBody(InstructorDTO::class.java).returnResult().responseBody

        Assertions.assertTrue {
            savedInstructorDTO!!.id != null
        }
    }
}