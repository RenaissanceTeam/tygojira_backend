package ru.fors.entity.auth

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "usr")
data class User(
        @Id
        val username: String,
        val password: String
)
