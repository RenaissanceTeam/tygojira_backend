package ru.fors.employee.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.entity.EmployeeNotFoundException
import ru.fors.employee.api.domain.entity.NoBusinessRoleException
import ru.fors.employee.api.domain.usecase.*
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeeRole
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.util.whenNotAllowedMapToResponseStatusException

@RestController
@RequestMapping("/employees")
class EmployeeController(
        private val addEmployeeUseCase: AddEmployeeUseCase,
        private val getFullEmployeesInfoUseCase: GetFullEmployeesInfoUseCase,
        private val updateEmployeeUseCase: UpdateEmployeeUseCase,
        private val deleteEmployeeUseCase: DeleteEmployeeUseCase,
        private val getEmployeeRoleByUsernameUseCase: GetEmployeeRoleByUsernameUseCase
) {

    @PostMapping("/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        return addEmployeeUseCase.runCatching { execute(employeeWithRoleDto) }
                .onFailure(::mapThrowable)
                .getOrThrow()
    }

    @PostMapping("")
    fun getFullEmployeeInfo(@RequestBody pageRequest: PageRequest): Page<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute(pageRequest)
    }

    @PostMapping("/{id}/update")
    fun updateEmployee(@PathVariable id: Long, @RequestBody updateDto: UpdateEmployeeInfoDto): Employee {
        return updateEmployeeUseCase.runCatching { execute(id, updateDto) }
                .onFailure(this::mapThrowable)
                .getOrThrow()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        deleteEmployeeUseCase.runCatching { execute(id) }
                .onFailure(::mapThrowable)
                .getOrThrow()
    }

    @GetMapping("/users/{username}")
    fun getEmployeeRoleByUsername(@PathVariable username: String): EmployeeRole {
        return getEmployeeRoleByUsernameUseCase.runCatching { execute(username) }
                .onFailure(::mapThrowable)
                .getOrThrow()
    }

    private fun mapThrowable(throwable: Throwable) {
        when (val it = throwable.whenNotAllowedMapToResponseStatusException()) {
            is EmployeeNotFoundException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, it.message)
            is NoBusinessRoleException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, it.message)
            else -> throw it
        }
    }
}