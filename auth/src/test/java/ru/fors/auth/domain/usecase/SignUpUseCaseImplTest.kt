package ru.fors.auth.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.entity.NotAllowedException
import ru.fors.auth.api.domain.entity.UserExistsException
import ru.fors.auth.api.domain.usecase.SignUpUseCase
import ru.fors.auth.data.SystemRoleRepo
import ru.fors.auth.data.UserRepo
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.auth.User
import kotlin.test.assertFailsWith

class SignUpUseCaseImplTest {

    private lateinit var useCase: SignUpUseCase
    private lateinit var userRepo: UserRepo
    private lateinit var systemRoleRepo: SystemRoleRepo

    @Before
    fun setUp() {
        userRepo = mock {}
        systemRoleRepo = mock {}

        useCase = SignUpUseCaseImpl(
                userRepo,
                systemRoleRepo
        )
    }

    @Test
    fun `when sign up user with login that already exists should throw`() {
        val takenUsername = "a"
        val creds = Credentials(takenUsername, "p")
        whenever(userRepo.findByUsername(takenUsername)).then { mock<User>() }

        assertFailsWith<UserExistsException> { useCase.execute(creds, SystemUserRole.ADMIN) }
    }

    @Test
    fun `when try to register SUPERUSER should throw`() {
        val creds = Credentials("asdfs", "asdf")

        assertFailsWith<NotAllowedException> { useCase.execute(creds, SystemUserRole.SUPERUSER) }
    }
}