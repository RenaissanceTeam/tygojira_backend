package ru.fors.entity.employee

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import javax.persistence.*

@Entity
data class Employee(
        @Id @GeneratedValue
        val id: Long = 0,
        val name: String,
        val position: String,
        @ElementCollection(targetClass = String::class, fetch = FetchType.LAZY)
        @Cascade(CascadeType.DELETE)
        val skills: List<String> = listOf(),
        @ElementCollection(targetClass = String::class, fetch = FetchType.LAZY)
        @Cascade(CascadeType.DELETE)
        val workRoles: List<String> = listOf()
)