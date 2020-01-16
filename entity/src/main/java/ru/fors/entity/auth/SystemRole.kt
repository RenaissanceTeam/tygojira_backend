package ru.fors.entity.auth

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
data class SystemRole(
        @Id @GeneratedValue
        val id: Long = 0,
        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user: User,
        @Enumerated(EnumType.ORDINAL)
        @Cascade(CascadeType.DELETE)
        val role: SystemUserRole
)