package ru.fors.employee.api.domain.entity

class EmployeeNotFoundException(id: Long): Throwable("No employee for id=$id")