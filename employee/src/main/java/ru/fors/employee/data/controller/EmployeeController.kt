package ru.fors.employee.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.fors.employee.Employee
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.AddEmployeeUseCase
import ru.fors.employee.api.domain.EmployeeNotFoundException
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.GetFullEmployeesInfoUseCase
import ru.fors.employee.api.domain.UpdateEmployeeUseCase
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto

@RestController
class EmployeeController(
        private val addEmployeeUseCase: AddEmployeeUseCase,
        private val getFullEmployeesInfoUseCase: GetFullEmployeesInfoUseCase,
        private val updateEmployeeUseCase: UpdateEmployeeUseCase
) {

    @PostMapping("employees/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        return addEmployeeUseCase.execute(employeeWithRoleDto)
    }

    @GetMapping("employees")
    fun getAll(): List<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute()
    }

    @PostMapping("employees/{id}/update")
    fun updateEmployee(@PathVariable id: Long, @RequestBody updateDto: UpdateEmployeeInfoDto): Employee {
        return updateEmployeeUseCase.runCatching { execute(id, updateDto) }.onFailure {
            when (it) {
                is EmployeeNotFoundException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, it.message)
            }
        }.getOrThrow()
    }
}