package ru.fors.entity.activity

import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.employee.Employee
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Activity(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        val name: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        @ManyToOne
        val lead: Employee? = null
)