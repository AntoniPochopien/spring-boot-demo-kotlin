package com.example.demo.controller

import com.example.demo.dto.CourseDTO
import com.example.demo.entity.Course
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
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: ICourseRepository

    @Autowired
    lateinit var instructorRepository: IInstructorRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()
        val instructors = instructorEntityList()
        instructorRepository.saveAll(instructors)
        val courses = courseEntityList(instructors.first())
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {
        val instructor = instructorRepository.findAll().first()

        val courseDTO = CourseDTO(null, "Build rest", "X", instructor.id)
        val savedCourseDTO = webTestClient.post().uri("api/courses").bodyValue(courseDTO).exchange()
            .expectStatus().isCreated.expectBody(CourseDTO::class.java).returnResult().responseBody

        Assertions.assertTrue {
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {
        val courseDTOs =
                webTestClient.get().uri("api/courses").exchange().expectStatus().isOk.expectBodyList(
                        CourseDTO::class.java
                ).returnResult().responseBody
        Assertions.assertEquals(3, courseDTOs!!.size)
    }

    @Test
    fun retrieveAllCourses_byName() {

      val uri =  UriComponentsBuilder.fromUriString("api/courses").queryParam("course_name", "name").toUriString()

        val courseDTOs =
            webTestClient.get().uri(uri).exchange().expectStatus().isOk.expectBodyList(
                CourseDTO::class.java
            ).returnResult().responseBody
        Assertions.assertEquals(3, courseDTOs!!.size)
    }

    @Test
    fun updateCourse() {
        val instructor = instructorRepository.findAll().first()

        val course = Course(id= null, name =  "test name1", category = "test1", instructor)
        courseRepository.save(course)
        val updatedCourseDTO = CourseDTO(id= null, name =  "test name2", category = "test1", instructor.id)

        val updatedCourse = webTestClient.put().uri("api/courses/{courseId}", course.id).bodyValue(updatedCourseDTO).exchange().expectStatus().isOk.expectBody(
            CourseDTO::class.java
        ).returnResult().responseBody
        Assertions.assertEquals("test name2", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {
        val instructor = instructorRepository.findAll().first()

        val course = Course(id= null, name =  "test name1", category = "test1", instructor)
        courseRepository.save(course)

        webTestClient.delete().uri("api/courses/{courseId}", course.id).exchange().expectStatus().isNoContent
    }
}