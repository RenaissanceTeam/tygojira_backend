package ru.fors.auth.domain

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.GetSystemRoleByUsername
import ru.fors.auth.data.SystemRoleRepo
import ru.fors.entity.auth.SystemRole

@Component
open class GetSystemRoleByUsernameImpl(
        private val systemRoleRepo: SystemRoleRepo
) : GetSystemRoleByUsername {
    override fun execute(username: String): SystemRole? {
        return systemRoleRepo.findByUserUsername(username)
    }
}