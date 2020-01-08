package ru.fors.auth.data.jwt

import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
open class JwtTokenProvider {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${app.jwtSecret}")
    private lateinit var jwtSecret: String
    @Value("\${app.jwtExpirationInMs}")
    private val jwtExpirationInMs = 0

    fun generateToken(username: String): String {

        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUsernameFromToken(token: String?): String {
        return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body.subject
    }

    fun validateToken(authToken: String?): Boolean = runCatching {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
    }.fold(
        onSuccess = { true },
        onFailure = {
            log.error(it.message, it.stackTrace)
            false
        }
    )

}