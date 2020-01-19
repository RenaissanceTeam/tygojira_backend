package ru.fors.auth.entity

import javax.persistence.*


@Entity
data class EmployeeRole(
        @Id @GeneratedValue
        val id: Long = 0,
        @OneToOne
        @JoinColumn
        val user: User,
        @ElementCollection(targetClass = String::class, fetch = FetchType.EAGER)
        val roles: Set<String>
)