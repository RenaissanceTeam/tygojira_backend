package ru.fors.auth.domain

import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ru.fors.auth.api.domain.SignUpUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.entity.NotAllowedException
import ru.fors.auth.api.domain.entity.UserExistsException
import ru.fors.auth.data.SystemRoleRepo
import ru.fors.auth.data.UserRepo
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.auth.User
import kotlin.test.assertFailsWith

class SignUpUseCaseImplTest {

    private lateinit var useCase: SignUpUseCase
    private lateinit var userRepo: UserRepo
    private lateinit var systemRoleRepo: SystemRoleRepo
    private lateinit var checkUserRole: CheckCallerHasSystemRoleUseCaseImpl

    @Before
    fun setUp() {
        userRepo = mock {}
        checkUserRole= mock {}
        systemRoleRepo= mock {}

        useCase = SignUpUseCaseImpl(
                userRepo,
                systemRoleRepo,
                checkUserRole
        )
    }

    @Test
    fun `when sign up user with login that already exists should throw`() {
        val takenUsername = "a"
        val creds = Credentials(takenUsername, "p")
        whenever(userRepo.findByUsername(takenUsername)).then { mock<User>() }

        whenever(checkUserRole.execute(any())).then { true }
        assertFailsWith<UserExistsException> { useCase.execute(creds, SystemUserRole.ADMIN) }
    }

    @Test
    fun `when registering ADMIN should have SUPERUSER rights`() {
        val creds = Credentials("asdfs", "asdf")
        whenever(userRepo.save(any<User>())).then { mock<User>() }

        whenever(checkUserRole.execute(SystemUserRole.SUPERUSER)).then { false }
        assertFailsWith<NotAllowedException> { useCase.execute(creds, SystemUserRole.ADMIN) }
        whenever(checkUserRole.execute(SystemUserRole.SUPERUSER)).then { true }
        assertDoesNotThrow { useCase.execute(creds, SystemUserRole.ADMIN) }

        verify(checkUserRole, times(2)).execute(SystemUserRole.SUPERUSER)
    }

    @Test
    fun `when registering USER should have ADMIN rights`() {
        val creds = Credentials("asdfs", "asdf")

        whenever(userRepo.save(any<User>())).then { mock<User>() }

        whenever(checkUserRole.execute(SystemUserRole.ADMIN)).then { true }
        useCase.execute(creds, SystemUserRole.USER)

        verify(checkUserRole).execute(SystemUserRole.ADMIN)
    }

    @Test
    fun `when try to register SUPERUSER should throw`() {
        val creds = Credentials("asdfs", "asdf")

        assertFailsWith<NotAllowedException> { useCase.execute(creds, SystemUserRole.SUPERUSER) }
    }
}