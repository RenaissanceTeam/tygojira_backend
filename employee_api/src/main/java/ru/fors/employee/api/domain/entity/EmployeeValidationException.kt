package ru.fors.employee.api.domain.entity

class EmployeeValidationException(value: String) : Throwable("$value is invalid")