package ru.fors.employee

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Employee(
        @Id @GeneratedValue
        val id: Long = 0,
        val name: String,
        val position: String
)