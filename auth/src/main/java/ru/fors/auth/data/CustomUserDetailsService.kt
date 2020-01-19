package ru.fors.auth.data

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.fors.employee.api.domain.GetEmployeeRoleUseCase


@Service
open class CustomUserDetailsService(
        private val userRepo: UserRepo,
        private val getEmployeeRoleUseCase: GetEmployeeRoleUseCase
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username) ?: throw UsernameNotFoundException("Not found $username")
        val roles = getEmployeeRoleUseCase.execute(username)?.roles ?: setOf()

        return UserPrincipal(user, roles)
    }
}