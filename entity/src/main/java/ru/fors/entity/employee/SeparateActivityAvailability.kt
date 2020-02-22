package ru.fors.entity.employee

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import ru.fors.entity.NOT_DEFINED_ID
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
data class SeparateActivityAvailability(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        val employee: Employee,
        @ElementCollection(targetClass = LocalDate::class, fetch = FetchType.EAGER)
        val dates: List<LocalDate> = listOf()
)