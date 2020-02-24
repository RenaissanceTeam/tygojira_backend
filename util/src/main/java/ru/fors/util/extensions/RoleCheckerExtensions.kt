package ru.fors.util.extensions

import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role

fun RoleChecker.requireOne(role: Role) {
    startCheck().require(role).requireAllSpecified()
}

fun RoleChecker.requireOne(role: SystemUserRole) {
    startCheck().require(role).requireAllSpecified()
}