package ru.fors.activity.api.domain.usecase

import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Employee

interface SetActivityLeadUseCase {
    fun execute(id: Long, employeeId: Long): Activity
}