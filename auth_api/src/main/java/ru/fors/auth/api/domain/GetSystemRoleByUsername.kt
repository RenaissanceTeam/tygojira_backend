package ru.fors.auth.api.domain

import ru.fors.entity.auth.SystemRole

interface GetSystemRoleByUsername {
    fun execute(username: String): SystemRole?
}