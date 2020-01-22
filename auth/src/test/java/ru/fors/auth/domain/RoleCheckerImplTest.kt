package ru.fors.auth.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role
import kotlin.test.assertFails

class RoleCheckerImplTest {

    private lateinit var checkSystem: CheckCallerHasSystemRoleUseCase
    private lateinit var checkBusiness: CheckUserHasBusinessRoleUseCase
    private lateinit var checker: RoleChecker

    @Before
    fun setUp() {
        checkSystem = mock {}
        checkBusiness = mock {}
        checker = RoleCheckerImpl(checkSystem, checkBusiness)
    }

    @Test
    fun `require LR role and check fails should throw`() {

        whenever(checkBusiness.execute(Role.LINEAR_LEAD)).then { false }
        assertFails {
            checker.startCheck()
                    .require(Role.LINEAR_LEAD)
                    .runOnFailureThrow()
        }
    }

    @Test
    fun `require LR role and check succeed should finish without exception`() {
        whenever(checkBusiness.execute(Role.LINEAR_LEAD)).then { true }
        assertDoesNotThrow {
            checker.startCheck()
                    .require(Role.LINEAR_LEAD)
                    .runOnFailureThrow()
        }
    }

    @Test
    fun `require two roles that pass check should not throw`() {
        whenever(checkBusiness.execute(Role.LINEAR_LEAD)).then { true }
        whenever(checkBusiness.execute(Role.USER)).then { true }
        assertDoesNotThrow {
            checker.startCheck()
                    .require(Role.LINEAR_LEAD)
                    .require(Role.USER)
                    .runOnFailureThrow()
        }
    }

    @Test
    fun `require two roles and one doesn't pass check should throw`() {
        whenever(checkBusiness.execute(Role.LINEAR_LEAD)).then { false }
        whenever(checkBusiness.execute(Role.USER)).then { true }
        assertFails {
            checker.startCheck()
                    .require(Role.USER)
                    .require(Role.LINEAR_LEAD)
                    .runOnFailureThrow()
        }
    }

    @Test
    fun `require any of two roles both pass checks should not throw`() {
        whenever(checkBusiness.execute(Role.LINEAR_LEAD)).then { true }
        whenever(checkSystem.execute(SystemUserRole.ADMIN)).then { true }
        assertDoesNotThrow {
            checker.startCheck()
                    .require(Role.LINEAR_LEAD)
                    .require(SystemUserRole.ADMIN)
                    .requireAnySpecified()
                    .runOnFailureThrow()
        }
    }

    @Test
    fun `require any of two roles one pass check should not throw`() {
        whenever(checkBusiness.execute(Role.LINEAR_LEAD)).then { false }
        whenever(checkSystem.execute(SystemUserRole.ADMIN)).then { true }
        assertDoesNotThrow {
            checker.startCheck()
                    .require(Role.LINEAR_LEAD)
                    .require(SystemUserRole.ADMIN)
                    .requireAnySpecified()
                    .runOnFailureThrow()
        }
    }

    @Test
    fun `require any of two roles none pass should throw`() {
        whenever(checkBusiness.execute(Role.LINEAR_LEAD)).then { false }
        whenever(checkSystem.execute(SystemUserRole.ADMIN)).then { false }
        assertFails {
            checker.startCheck()
                    .require(Role.LINEAR_LEAD)
                    .require(SystemUserRole.ADMIN)
                    .requireAnySpecified()
                    .runOnFailureThrow()
        }
    }
}