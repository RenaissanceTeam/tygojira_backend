package ru.fors.auth.data.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.fors.auth.domain.entity.User
import ru.fors.auth.domain.entity.UserRole

class UserPrincipal(private val user: User, private val userRole: UserRole) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        userRole.roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()
    
    override fun isEnabled() = true
    
    override fun getUsername() = user.username
    
    override fun isCredentialsNonExpired() = true
    
    override fun getPassword() = user.password
    
    override fun isAccountNonExpired() = true
    
    override fun isAccountNonLocked() = true
}