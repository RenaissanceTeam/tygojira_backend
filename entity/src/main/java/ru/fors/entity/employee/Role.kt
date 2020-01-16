package ru.fors.entity.employee

enum class Role {
    USER, PROJECT_LEAD, LINEAR_LEAD, PROJECT_OFFICE;

    val role
        get() = "ROLE_$this"
}
