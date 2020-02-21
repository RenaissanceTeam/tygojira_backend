package ru.fors.employee.api.domain.entity

class NoEmployeeLinkedWithUserException(username: String): Throwable("No user $username linked with employee")