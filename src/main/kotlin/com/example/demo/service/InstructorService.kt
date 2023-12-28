package com.example.demo.service

import com.example.demo.dto.InstructorDTO
import com.example.demo.entity.Instructor
import com.example.demo.repository.IInstructorRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class InstructorService(val instructorRepository: IInstructorRepository) {
    fun createInstructor(instructorDto: InstructorDTO):InstructorDTO{
      val instructorEntity =  instructorDto.let {
            Instructor(it.id, it.name)
        }
        instructorRepository.save(instructorEntity)
        return instructorEntity.let { InstructorDTO(it.id, it.name, it.courses) }
    }

    fun retrieveAllInstructors():List<InstructorDTO>{
        return instructorRepository.findAll().map {
            InstructorDTO(it.id, it.name, it.courses)
        }
    }

    fun findByInstructorId(instructorId: Int) :Optional<Instructor>{
        return instructorRepository.findById(instructorId)
    }
}