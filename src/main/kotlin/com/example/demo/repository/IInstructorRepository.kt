package com.example.demo.repository

import com.example.demo.entity.Instructor
import org.springframework.data.repository.CrudRepository


interface IInstructorRepository : CrudRepository<Instructor, Int>