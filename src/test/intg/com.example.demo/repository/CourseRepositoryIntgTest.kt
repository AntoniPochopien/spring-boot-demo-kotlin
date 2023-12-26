package com.example.demo.repository

import com.example.demo.utils.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: ICourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        val courses = courseEntityList()
        courseRepository.saveAll(courses)
    }

    @Test
    fun findByNameContaining(){
     val courses =  courseRepository.findByNameContaining("name")
        println("courses: $courses")
        Assertions.assertEquals(3, courses.size)
    }

    @Test
    fun findCoursesByName(){
        val courses =  courseRepository.findCoursesByName("name")
        println("courses: $courses")
        Assertions.assertEquals(3, courses.size)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName_approach2(name:String, expectedSize:Int){
        val courses =  courseRepository.findCoursesByName(name)
        println("courses: $courses")
        Assertions.assertEquals(expectedSize, courses.size)
    }

    companion object{
        @JvmStatic
        fun courseAndSize() :Stream<Arguments>{
            return Stream.of(Arguments.arguments("name", 3),Arguments.arguments("2", 1), )
        }
    }
}