package com.example.demo.utils

import com.example.demo.entity.Course
import com.example.demo.entity.Instructor

fun courseEntityList(instructor: Instructor? = null) = listOf(
    Course(id= null, name =  "test name1", category = "test1", instructor),
    Course(id= null, name =  "test name2", category = "test2",instructor),
    Course(id= null, name =  "test name3", category = "test3", instructor),
)
fun instructorEntityList() = listOf(
        Instructor(id= null, name =  "instructor 1"),
        Instructor(id= null, name =  "instructor 2"),
)