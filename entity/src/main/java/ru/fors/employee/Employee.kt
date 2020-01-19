package ru.fors.employee

import javax.persistence.*

@Entity
data class Employee(
        @Id @GeneratedValue
        val id: Long = 0,
        val name: String,
        val position: String,
        @ElementCollection(targetClass = String::class, fetch = FetchType.LAZY)
        val skills: List<String> = listOf(),
        @ElementCollection(targetClass = String::class, fetch = FetchType.LAZY)
        val workRoles: List<String> = listOf()
)