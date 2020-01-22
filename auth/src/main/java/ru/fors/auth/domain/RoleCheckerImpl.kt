package ru.fors.auth.domain

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.entity.NotAllowedException
import ru.fors.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role

@Component
open class RoleCheckerImpl(
        private val checkCallerHasSystemRoleUseCase: CheckCallerHasSystemRoleUseCase,
        private val checkUserHasBusinessRoleUseCase: CheckUserHasBusinessRoleUseCase
) : RoleChecker {
    override fun startCheck(): RoleChecker.Setup {
        return SetupImpl()
    }

    inner class SetupImpl : RoleChecker.Setup {
        private val systemRoles = mutableListOf<SystemUserRole>()
        private val businessRoles = mutableListOf<Role>()
        private var requireAllSpecifiedRoles = true

        override fun require(role: Role) = apply {
            businessRoles.add(role)
        }

        override fun require(role: SystemUserRole) = apply {
            systemRoles.add(role)
        }

        override fun requireAnySpecified() = apply {
            requireAllSpecifiedRoles = false
        }

        override fun requireAllSpecified() = apply {
            requireAllSpecifiedRoles = true
        }

        override fun runOnFailureThrow() {
            val checks = systemRoles.map { lazy { checkCallerHasSystemRoleUseCase.execute(it) } } +
                    businessRoles.map { lazy { checkUserHasBusinessRoleUseCase.execute(it) } }

            when (requireAllSpecifiedRoles) {
                true -> if (checks.all { it.value }.not()) throwNotAllowed()
                false -> if (checks.any { it.value }.not()) throwNotAllowed()
            }
        }

        private fun throwNotAllowed() {
            val infix = if (requireAllSpecifiedRoles) "all" else "any"

            throw NotAllowedException("required $infix roles: ${systemRoles + businessRoles}")
        }
    }
}