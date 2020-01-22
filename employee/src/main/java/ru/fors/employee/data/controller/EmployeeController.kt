package ru.fors.employee.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.EmployeeNotFoundException
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.usecase.AddEmployeeUseCase
import ru.fors.employee.api.domain.usecase.DeleteEmployeeUseCase
import ru.fors.employee.api.domain.usecase.GetFullEmployeesInfoUseCase
import ru.fors.employee.api.domain.usecase.UpdateEmployeeUseCase
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role
import ru.fors.util.runOnFailureThrowSpringNotAllowed

@RestController
@RequestMapping("/employees")
class EmployeeController(
        private val addEmployeeUseCase: AddEmployeeUseCase,
        private val getFullEmployeesInfoUseCase: GetFullEmployeesInfoUseCase,
        private val updateEmployeeUseCase: UpdateEmployeeUseCase,
        private val deleteEmployeeUseCase: DeleteEmployeeUseCase,
        private val roleChecker: RoleChecker
) {

    @PostMapping("/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        roleChecker.startCheck()
                .requireAnySpecified()
                .require(SystemUserRole.ADMIN)
                .require(Role.LINEAR_LEAD)
                .runOnFailureThrowSpringNotAllowed()

        return addEmployeeUseCase.execute(employeeWithRoleDto)
    }

    @GetMapping("")
    fun getAll(): List<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute()
    }

    @PostMapping("/{id}/update")
    fun updateEmployee(@PathVariable id: Long, @RequestBody updateDto: UpdateEmployeeInfoDto): Employee {
        roleChecker.startCheck().require(Role.LINEAR_LEAD).runOnFailureThrowSpringNotAllowed()

        return updateEmployeeUseCase.runCatching { execute(id, updateDto) }.onFailure(this::mapThrowable).getOrThrow()
    }

    private fun mapThrowable(it: Throwable) {
        when (it) {
            is EmployeeNotFoundException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, it.message)
        }
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long) {
        roleChecker.startCheck().require(Role.LINEAR_LEAD).runOnFailureThrowSpringNotAllowed()

        deleteEmployeeUseCase.runCatching { execute(id) }.onFailure(this::mapThrowable).getOrThrow()
    }
}