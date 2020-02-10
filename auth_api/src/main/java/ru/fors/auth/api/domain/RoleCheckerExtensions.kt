package ru.fors.auth.api.domain

import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role

fun RoleChecker.requireOne(role: Role) {
    startCheck().require(role).requireAllSpecified()
}

fun RoleChecker.requireOne(role: SystemUserRole) {
    startCheck().require(role).requireAllSpecified()
}