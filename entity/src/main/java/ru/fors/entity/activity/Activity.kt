package ru.fors.entity.activity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Activity(
        @Id @GeneratedValue
        val id: Long = 0,
        val name: String,
        val startDate: Date,
        val endDate: Date
)