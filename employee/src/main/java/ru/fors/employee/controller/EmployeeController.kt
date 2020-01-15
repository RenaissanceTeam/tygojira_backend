package ru.fors.employee.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.fors.employee.api.usecase.AddEmployeeUseCase
import ru.fors.employee.api.usecase.dto.EmployeeWithRoleDto

@RestController
class EmployeeController(
        private val addEmployeeUseCase: AddEmployeeUseCase
) {



    @PostMapping("employees/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto) {
        addEmployeeUseCase.execute(employeeWithRoleDto)
    }
}