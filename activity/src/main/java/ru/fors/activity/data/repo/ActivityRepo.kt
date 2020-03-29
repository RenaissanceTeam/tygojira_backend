package ru.fors.activity.data.repo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Employee

interface ActivityRepo : JpaRepository<Activity, Long> {
    fun findByLead(employee: Employee): List<Activity>
    fun findAllByLead(lead: Employee, pageable: Pageable): Page<Activity>
    fun findAllByLeadSubdivision(subdivision: String, pageable: Pageable): Page<Activity>
    fun findAllByIdIn(ids: List<Long>, pageable: Pageable): Page<Activity>
}