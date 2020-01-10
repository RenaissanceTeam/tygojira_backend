package ru.fors.auth.entity

import javax.persistence.*

@Entity
data class SystemRole(
        @Id @GeneratedValue
        val id: Long = 0,
        @OneToOne
        @JoinColumn(name = "username")
        val user: User,
        @Enumerated(EnumType.ORDINAL)
        val role: SystemUserRoles
)