package ru.fors.auth.data.jwt

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.fors.auth.data.UserRepo
import ru.fors.auth.data.EmployeeRoleRepo


@Service
open class CustomUserDetailsService(
        private val userRepo: UserRepo,
        private val employeeRoleRepo: EmployeeRoleRepo
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username) ?: throw UsernameNotFoundException("Not found $username")
        val roles = employeeRoleRepo.findByUserUsername(username) ?: throw UsernameNotFoundException("Empty role for $username")

        return UserPrincipal(user, roles)
    }
}