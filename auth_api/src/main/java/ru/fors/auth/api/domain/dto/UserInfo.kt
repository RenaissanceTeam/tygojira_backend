package ru.fors.auth.api.domain.dto

import ru.fors.entity.auth.SystemUserRole

data class UserInfo(
        val username: String,
        val systemUserRole: SystemUserRole
)