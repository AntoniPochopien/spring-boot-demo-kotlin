package com.example.demo.dto

import com.example.demo.entity.Course

data class InstructorDTO (
        val id: Int?,
        var name:String,
        var courses:List<Course>,
)