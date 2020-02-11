package ru.fors.employee.api.domain.entity

class NoLinkedToEmployeeUserException(username: String): Throwable("No user $username linked with employee")