package ru.fors.employee.api.domain

class EmployeeNotFoundException(id: Long): Throwable("No employee for id=$id")