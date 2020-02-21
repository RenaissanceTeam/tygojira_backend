package ru.fors.employee.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.employee.SeparateActivityAvailability


interface SeparateActivityAvailabilityRepo : JpaRepository<SeparateActivityAvailability, Long> {
    fun findByEmployeeId(id: Long): SeparateActivityAvailability?
}