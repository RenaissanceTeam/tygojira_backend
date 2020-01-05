package ru.fors.auth.domain.entity

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id


@Entity
data class UserRole(
        @Id
        val username: String,
        @ElementCollection(targetClass = String::class, fetch = FetchType.EAGER)
        val roles: Set<String>
)