package com.example.demo.repository

import com.example.demo.entity.Course
import org.springframework.data.repository.CrudRepository

interface ICourseRepository : CrudRepository<Course, Int>