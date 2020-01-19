package ru.fors.employee

import javax.persistence.*

@Entity
data class Employee(
        @Id @GeneratedValue
        val id: Long = 0,
        val name: String,
        val position: String
)