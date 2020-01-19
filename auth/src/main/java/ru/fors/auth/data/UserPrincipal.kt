package ru.fors.auth.data

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.fors.entity.auth.User
import ru.fors.entity.employee.Role

class UserPrincipal(private val user: User,
                    private val roles: Set<Role>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            roles
                    .map { SimpleGrantedAuthority("ROLE_$it") }
                    .ifEmpty { listOf(SimpleGrantedAuthority("ROLE_UNKNOWN")) }
                    .toMutableList()


    override fun isEnabled() = true

    override fun getUsername() = user.username

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = user.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}