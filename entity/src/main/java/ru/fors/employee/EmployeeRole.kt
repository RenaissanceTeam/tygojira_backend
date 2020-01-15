package ru.fors.employee

import javax.persistence.*

@Entity
data class EmployeeRole(
        @Id @GeneratedValue
        val id: Long = 0,
        @OneToOne
        val employee: Employee,
        @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        val roles: Set<Role>
)