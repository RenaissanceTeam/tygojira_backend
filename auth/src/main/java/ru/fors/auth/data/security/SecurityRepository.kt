package ru.fors.auth.data.security

interface SecurityRepository {
    fun getAuthenticatedUsername(): String
}