package ru.fors.entity.employee

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
data class EmployeeRole(
        @Id @GeneratedValue
        val id: Long = 0,
        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        val employee: Employee,
        @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn
        val roles: Set<Role>
)