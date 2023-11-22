package com.example.demo.service

import com.example.demo.dto.CourseDTO
import com.example.demo.entity.Course
import com.example.demo.repository.ICourseRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}


@Service
class CourseService(val courseRepository: ICourseRepository) {

    fun addCourse(courseDto: CourseDTO) : CourseDTO{
        val courseEntity = courseDto.let {
            Course(null, it.name, it.category)
        }
        courseRepository.save(courseEntity)

        logger.info { "Saved course is: $courseEntity" }

        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }
}