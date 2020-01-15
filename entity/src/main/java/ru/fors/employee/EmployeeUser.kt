package ru.fors.employee

import ru.fors.auth.entity.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class EmployeeUser(
        @Id @GeneratedValue
        val id: Long = 0,
        @OneToOne
        val employee: Employee,
        @OneToOne
        val user: User
)