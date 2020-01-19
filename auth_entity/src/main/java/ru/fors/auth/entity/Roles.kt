package ru.fors.auth.entity

enum class Roles {
    USER, PROJECT_LEAD, LINEAR_LEAD, PROJECT_OFFICE;

    val role
        get() = "ROLE_$this"
}
