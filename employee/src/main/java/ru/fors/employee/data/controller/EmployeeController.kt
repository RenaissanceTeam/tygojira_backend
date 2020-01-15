package ru.fors.employee.data.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.fors.employee.Employee
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.AddEmployeeUseCase

@RestController
class EmployeeController(
        private val addEmployeeUseCase: AddEmployeeUseCase
) {

    @PostMapping("employees/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        return addEmployeeUseCase.execute(employeeWithRoleDto)
    }
}