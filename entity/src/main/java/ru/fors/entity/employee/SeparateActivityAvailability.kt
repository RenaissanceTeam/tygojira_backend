package ru.fors.entity.employee

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
data class SeparateActivityAvailability(
        @Id @GeneratedValue
        val id: Long = 0,
        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        val employee: Employee,
        @ElementCollection(targetClass = Date::class, fetch = FetchType.EAGER)
        val dates: List<Date> = listOf()
)