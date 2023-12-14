package com.example.demo.exception

import java.lang.RuntimeException

class CourseNotFoundException(message: String) : RuntimeException(message)
