package ru.fors.auth.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ru.fors.auth.api.domain.GetCallingUserUseCase
import ru.fors.auth.api.domain.GetSystemRoleByUsername
import ru.fors.auth.api.domain.SignUpUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.entity.NotAllowedException
import ru.fors.auth.api.domain.entity.UserExistsException
import ru.fors.auth.data.SystemRoleRepo
import ru.fors.auth.data.UserRepo
import ru.fors.auth.entity.SystemRole
import ru.fors.auth.entity.SystemUserRoles
import ru.fors.auth.entity.User
import kotlin.test.assertFailsWith

class SignUpUseCaseImplTest {

    private lateinit var useCase: SignUpUseCase
    private lateinit var userRepo: UserRepo
    private lateinit var systemRoleRepo: SystemRoleRepo
    private lateinit var getCallingUserUseCase: GetCallingUserUseCase
    private lateinit var getSystemRoleByUsername: GetSystemRoleByUsername
    private lateinit var employeeRepo: EmployeeRoleRepo

    @Before
    fun setUp() {
        userRepo = mock {}
        systemRoleRepo = mock {}
        getCallingUserUseCase = mock {}
        getSystemRoleByUsername = mock {}
        employeeRepo = mock {}
        useCase = SignUpUseCaseImpl(
                userRepo,
                systemRoleRepo,
                getCallingUserUseCase,
                getSystemRoleByUsername,
                employeeRepo
        )
    }

    @Test
    fun `when sign up user with login that already exists should throw`() {
        val takenUsername = "a"
        val creds = Credentials(takenUsername, "p")
        whenever(userRepo.findByUsername(takenUsername)).then { mock<User>() }

        assertFailsWith<UserExistsException> { useCase.execute(creds, SystemUserRoles.ADMIN) }
    }

    @Test
    fun `when registering ADMIN should have SUPERUSER rights`() {
        val creds = Credentials("asdfs", "asdf")

        val notRightfulUsername = "a"
        val rightfulUsername = "b"
        val notRightful = mock<User> { on { username }.then { notRightfulUsername } }
        val rightful = mock<User> { on { username }.then { rightfulUsername } }
        whenever(getSystemRoleByUsername.execute(rightfulUsername)).then { SystemRole(user = mock(), role = SystemUserRoles.SUPERUSER) }
        whenever(getSystemRoleByUsername.execute(notRightfulUsername)).then { SystemRole(user = mock(), role = SystemUserRoles.ADMIN) }
        whenever(userRepo.save(any<User>())).then { mock<User>() }


        whenever(getCallingUserUseCase.execute()).then { notRightful }
        assertFailsWith<NotAllowedException> { useCase.execute(creds, SystemUserRoles.ADMIN) }
        whenever(getCallingUserUseCase.execute()).then { rightful }
        assertDoesNotThrow { useCase.execute(creds, SystemUserRoles.ADMIN) }

    }

    @Test
    fun `when registering USER should have ADMIN rights`() {
        val creds = Credentials("asdfs", "asdf")

        val notRightfulUsername = "a"
        val rightfulUsername = "b"
        val notRightful = mock<User> { on { username }.then { notRightfulUsername } }
        val rightful = mock<User> { on { username }.then { rightfulUsername } }
        whenever(getSystemRoleByUsername.execute(rightfulUsername)).then { SystemRole(user = mock(), role = SystemUserRoles.ADMIN) }
        whenever(getSystemRoleByUsername.execute(notRightfulUsername)).then { SystemRole(user = mock(), role = SystemUserRoles.USER) }
        whenever(userRepo.save(any<User>())).then { mock<User>() }


        whenever(getCallingUserUseCase.execute()).then { notRightful }
        assertFailsWith<NotAllowedException> { useCase.execute(creds, SystemUserRoles.USER) }
        whenever(getCallingUserUseCase.execute()).then { rightful }
        assertDoesNotThrow { useCase.execute(creds, SystemUserRoles.USER) }
    }

    @Test
    fun `when try to register SUPERUSER should throw`() {
        val creds = Credentials("asdfs", "asdf")

        assertFailsWith<NotAllowedException> { useCase.execute(creds, SystemUserRoles.SUPERUSER) }
    }
}