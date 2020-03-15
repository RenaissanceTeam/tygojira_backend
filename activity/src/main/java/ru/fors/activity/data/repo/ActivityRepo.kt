package ru.fors.activity.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Employee

interface ActivityRepo : JpaRepository<Activity, Long> {
    fun findByLead(employee: Employee): List<Activity>
}