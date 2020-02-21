package ru.fors.entity.auth

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import ru.fors.entity.NOT_DEFINED_ID
import javax.persistence.*

@Entity
data class SystemRole(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user: User,
        @Enumerated(EnumType.ORDINAL)
        @Cascade(CascadeType.DELETE)
        val role: SystemUserRole
)