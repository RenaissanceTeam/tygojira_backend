package ru.fors.auth.domain

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import ru.fors.auth.api.domain.SignInUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.data.AuthenticatorAdapter
import ru.fors.auth.data.jwt.JwtTokenProvider
import kotlin.test.assertFails

class SignInUseCaseImplTest {

    private lateinit var useCase: SignInUseCase
    private lateinit var authenticator: AuthenticatorAdapter
    private lateinit var tokenProvider: JwtTokenProvider

    @Before
    fun setUp() {
        authenticator = mock {}
        tokenProvider = mock {}
        useCase = SignInUseCaseImpl(authenticator, tokenProvider)
    }

    @Test
    fun `when authenticator fails should rethrow`() {
        val exception = RuntimeException("asdf")
        val creds = Credentials("login", "pass")
        whenever(authenticator.authenticate(creds)).thenThrow(exception)

        assertFails { useCase.execute(creds) }
    }

    @Test
    fun `when authenticator completes successfully should generate token and return it`() {
        val creds = Credentials("login", "pass")

        val token = "token"
        whenever(tokenProvider.generateToken(creds.login)).then { token }

        assertThat(useCase.execute(creds).token).isEqualTo(token)
    }
}