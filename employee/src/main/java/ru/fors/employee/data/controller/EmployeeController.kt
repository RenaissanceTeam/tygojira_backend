package ru.fors.employee.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.entity.EmployeeNotFoundException
import ru.fors.employee.api.domain.usecase.AddEmployeeUseCase
import ru.fors.employee.api.domain.usecase.DeleteEmployeeUseCase
import ru.fors.employee.api.domain.usecase.GetFullEmployeesInfoUseCase
import ru.fors.employee.api.domain.usecase.UpdateEmployeeUseCase
import ru.fors.entity.employee.Employee
import ru.fors.pagination.api.domain.entity.Direction
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.pagination.api.domain.entity.Sort
import ru.fors.util.whenNotAllowedMapToResponseStatusException

@RestController
@RequestMapping("/employees")
class EmployeeController(
        private val addEmployeeUseCase: AddEmployeeUseCase,
        private val getFullEmployeesInfoUseCase: GetFullEmployeesInfoUseCase,
        private val updateEmployeeUseCase: UpdateEmployeeUseCase,
        private val deleteEmployeeUseCase: DeleteEmployeeUseCase
) {

    @PostMapping("/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        return addEmployeeUseCase.runCatching { execute(employeeWithRoleDto) }
                .onFailure(::mapThrowable)
                .getOrThrow()
    }

    @GetMapping("")
    fun getFullEmployeeInfoPost(
            @RequestParam page: Int,
            @RequestParam size: Int,
            @RequestParam direction: Direction,
            @RequestParam fields: List<String>
    ): Page<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute(PageRequest(
                page,
                size,
                Sort(direction, fields)
        ))
    }

    @PostMapping("/{id}/update")
    fun updateEmployee(@PathVariable id: Long, @RequestBody updateDto: UpdateEmployeeInfoDto): Employee {
        return updateEmployeeUseCase.runCatching { execute(id, updateDto) }
                .onFailure(this::mapThrowable)
                .getOrThrow()
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long) {
        deleteEmployeeUseCase.runCatching { execute(id) }
                .onFailure(::mapThrowable)
                .getOrThrow()
    }

    private fun mapThrowable(throwable: Throwable) {
        when (val it = throwable.whenNotAllowedMapToResponseStatusException()) {
            is EmployeeNotFoundException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, it.message)
            else -> throw it
        }
    }
}