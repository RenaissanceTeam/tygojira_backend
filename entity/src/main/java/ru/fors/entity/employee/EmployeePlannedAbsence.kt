package ru.fors.entity.employee

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import ru.fors.entity.NOT_DEFINED_ID
import java.time.LocalDate
import javax.persistence.*

@Entity
data class EmployeePlannedAbsence(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        val employee: Employee,
        @ElementCollection
        val dates: List<LocalDate> = emptyList()
)