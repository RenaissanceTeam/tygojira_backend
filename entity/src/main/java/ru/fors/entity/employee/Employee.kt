package ru.fors.entity.employee

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import ru.fors.entity.NOT_DEFINED_ID
import javax.persistence.*

@Entity
data class Employee(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        val firstName: String,
        val middleName: String,
        val lastName: String,
        val position: String,
        val subdivision: String,
        @ElementCollection(targetClass = String::class, fetch = FetchType.LAZY)
        @Cascade(CascadeType.DELETE)
        val skills: List<String> = listOf()
)