package ru.fors.employee.api.domain.entity

import ru.fors.entity.employee.Employee

class NoBusinessRoleException(employee: Employee? = null)
    : Throwable("No business role found ${employee?.let { "for employee $it" }.orEmpty()}")