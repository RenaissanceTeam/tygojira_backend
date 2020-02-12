package ru.fors.entity.employee

enum class Role {
    EMPLOYEE, PROJECT_LEAD, LINEAR_LEAD, PROJECT_OFFICE;

    val role
        get() = "ROLE_$this"
}
