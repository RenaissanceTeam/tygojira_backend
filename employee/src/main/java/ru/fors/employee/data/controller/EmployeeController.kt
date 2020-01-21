package ru.fors.employee.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.fors.entity.employee.Employee
import ru.fors.employee.api.domain.*
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.usecase.*
import ru.fors.employee.data.extensions.throwWhenNotAllowed
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role

@RestController
@RequestMapping("/employees")
class EmployeeController(
        private val addEmployeeUseCase: AddEmployeeUseCase,
        private val getFullEmployeesInfoUseCase: GetFullEmployeesInfoUseCase,
        private val updateEmployeeUseCase: UpdateEmployeeUseCase,
        private val deleteEmployeeUseCase: DeleteEmployeeUseCase,
        private val checkCallerHasSystemRoleUseCase: CheckCallerHasSystemRoleUseCase,
        private val checkUserHasBusinessRoleUseCase: CheckUserHasBusinessRoleUseCase
) {

    @PostMapping("/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        val isAdmin by lazy { checkCallerHasSystemRoleUseCase.execute(SystemUserRole.ADMIN) }
        val isLinearLead by lazy { checkUserHasBusinessRoleUseCase.execute(Role.LINEAR_LEAD) }

        if (!(isAdmin || isLinearLead)) {
            throw ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Must be either an ADMIN or LINEAR_LEAD")
        }
        
        return addEmployeeUseCase.execute(employeeWithRoleDto)
    }

    @GetMapping("")
    fun getAll(): List<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute()
    }

    @PostMapping("/{id}/update")
    fun updateEmployee(@PathVariable id: Long, @RequestBody updateDto: UpdateEmployeeInfoDto): Employee {
        checkUserHasBusinessRoleUseCase.throwWhenNotAllowed(Role.LINEAR_LEAD)

        return updateEmployeeUseCase.runCatching { execute(id, updateDto) }.onFailure(this::mapThrowable).getOrThrow()
    }

    private fun mapThrowable(it: Throwable) {
        when (it) {
            is EmployeeNotFoundException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, it.message)
        }
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long) {
        checkUserHasBusinessRoleUseCase.throwWhenNotAllowed(Role.LINEAR_LEAD)

        deleteEmployeeUseCase.runCatching { execute(id) }.onFailure(this::mapThrowable).getOrThrow()
    }
}