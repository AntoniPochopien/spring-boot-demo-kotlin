package com.example.demo.service

import com.example.demo.dto.CourseDTO
import com.example.demo.entity.Course
import com.example.demo.exception.CourseNotFoundException
import com.example.demo.exception.InstructorNotValidException
import com.example.demo.repository.ICourseRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.lang.Exception


private val logger = KotlinLogging.logger {}


@Service
class CourseService(val courseRepository: ICourseRepository, val instructorService: InstructorService) {

    fun addCourse(courseDto: CourseDTO) : CourseDTO{
      val instructorOptional = instructorService.findByInstructorId(courseDto.instructorId!!)
        if(!instructorOptional.isPresent){
            throw InstructorNotValidException("Instructor is not valid: ${courseDto.instructorId}")
        }
        val courseEntity = courseDto.let {
            Course(null, it.name, it.category, instructorOptional.get())
        }
        courseRepository.save(courseEntity)

        logger.info { "Saved course is: $courseEntity" }

        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category, it.instructor!!.id)
        }
    }

    fun retrieveAllCourses(courseName:String?): List<CourseDTO> {
        return courseRepository.findAll().map {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO{
        val existingCourse = courseRepository.findById(courseId)
        return if(existingCourse.isPresent){
           existingCourse.get().let {
               it.name = courseDTO.name
               it.category = courseDTO.category
               courseRepository.save(it)
               CourseDTO(it.id, it.name, it.category)
           }
       }else{
           throw CourseNotFoundException("No course found for the passed Id : $courseId")
       }
    }

    fun deleteCourse(courseId: Int){
        val existingCourse = courseRepository.findById(courseId)
         if(existingCourse.isPresent){
            existingCourse.get().let {
                courseRepository.deleteById(courseId)
            }
        }else{
            throw CourseNotFoundException("No course found for the passed Id : $courseId")
        }
    }
}