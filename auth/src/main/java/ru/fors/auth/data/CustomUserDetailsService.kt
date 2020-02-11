package ru.fors.auth.data

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.fors.auth.data.repo.UserRepo
import ru.fors.employee.api.domain.usecase.GetEmployeeRoleByUsernameUseCase

@Service
open class CustomUserDetailsService(
        private val userRepo: UserRepo,
        private val getEmployeeRoleByUsernameUseCase: GetEmployeeRoleByUsernameUseCase
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username) ?: throw UsernameNotFoundException("Not found $username")
        val roles = getEmployeeRoleByUsernameUseCase.runCatching { execute(username).roles }
                .getOrElse { setOf() }

        return UserPrincipal(user, roles)
    }
}