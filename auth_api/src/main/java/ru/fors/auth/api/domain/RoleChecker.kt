package ru.fors.auth.api.domain

import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role

interface RoleChecker {

    interface Setup {
        fun require(vararg roles: Role): Setup
        fun require(vararg roles: SystemUserRole): Setup
        fun requireAnySpecified()
        fun requireAllSpecified()
    }

    fun startCheck(): Setup
}