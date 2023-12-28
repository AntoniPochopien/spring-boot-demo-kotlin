package com.example.demo.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "Instructors")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    @get:NotBlank(message = "InstructorDTO.name must not be blank")
    var name:String,
    @OneToMany(
            mappedBy = "instructor",
            cascade = [CascadeType.ALL]
    )
        var courses:List<Course> = mutableListOf()
)