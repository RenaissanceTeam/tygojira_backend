package ru.fors.employee

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class EmployeeRole(
        @Id @GeneratedValue
        val id: Long = 0,
        @OneToOne
        val employee: Employee,
        val role: String
)