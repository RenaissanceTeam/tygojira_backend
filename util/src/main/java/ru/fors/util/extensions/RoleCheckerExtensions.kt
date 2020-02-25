package ru.fors.util.extensions

import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role

fun RoleChecker.requireAll(vararg roles: Role) {
    startCheck().require(*roles).requireAllSpecified()
}

fun RoleChecker.requireAny(vararg roles: Role) {
    startCheck().require(*roles).requireAnySpecified()
}


fun RoleChecker.requireOne(role: Role) {
    startCheck().require(role).requireAllSpecified()
}

fun RoleChecker.requireOne(role: SystemUserRole) {
    startCheck().require(role).requireAllSpecified()
}