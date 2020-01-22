package ru.fors.auth.api.domain

import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role

interface RoleChecker {

    interface Setup {
        fun require(role: Role): Setup
        fun require(role: SystemUserRole): Setup
        fun requireAnySpecified(): Setup
        fun requireAllSpecified(): Setup
        fun runOnFailureThrow()
    }

    fun startCheck(): Setup
}