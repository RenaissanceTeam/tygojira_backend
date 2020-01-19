package ru.fors.auth.api.domain

import ru.fors.auth.entity.SystemRole

interface GetSystemRoleByUsername {
    fun execute(username: String): SystemRole?
}