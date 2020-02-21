package ru.fors.entity.activity

import ru.fors.entity.NOT_DEFINED_ID
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Activity(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        val name: String,
        val startDate: Date,
        val endDate: Date
)