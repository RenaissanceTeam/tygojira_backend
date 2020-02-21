package ru.fors.employee.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.usecase.*
import ru.fors.employee.data.dto.SeparateActivityAvailabilityDto
import ru.fors.employee.data.mapper.AvailabilityToDtoMapper
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeeRole
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.util.StringDateMapper
import ru.fors.util.withEntityExceptionsMapper

@RestController
@RequestMapping("/employees")
class EmployeeController(
        private val addEmployeeUseCase: AddEmployeeUseCase,
        private val getFullEmployeesInfoUseCase: GetFullEmployeesInfoUseCase,
        private val updateEmployeeUseCase: UpdateEmployeeUseCase,
        private val deleteEmployeeUseCase: DeleteEmployeeUseCase,
        private val getEmployeeRoleByUsernameUseCase: GetEmployeeRoleByUsernameUseCase,
        private val setAvailableForSeparateActivitiesUseCase: SetAvailableForSeparateActivitiesUseCase,
        private val getAvailabilityForSeparateActivitiesUseCase: GetAvailabilityForSeparateActivitiesUseCase,
        private val availabilityToDtoMapper: AvailabilityToDtoMapper,
        private val stringDateMapper: StringDateMapper
) {

    @PostMapping("/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        return addEmployeeUseCase.runCatching { execute(employeeWithRoleDto) }
                .withEntityExceptionsMapper()
                .getOrThrow()
    }

    @PostMapping("")
    fun getFullEmployeeInfo(@RequestBody pageRequest: PageRequest): Page<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute(pageRequest)
    }

    @PostMapping("/{id}/update")
    fun updateEmployee(@PathVariable id: Long, @RequestBody updateDto: UpdateEmployeeInfoDto): Employee {
        return updateEmployeeUseCase.runCatching { execute(id, updateDto) }
                .withEntityExceptionsMapper()
                .getOrThrow()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        deleteEmployeeUseCase.runCatching { execute(id) }
                .withEntityExceptionsMapper()
                .getOrThrow()
    }

    @GetMapping("/users/{username}")
    fun getEmployeeRoleByUsername(@PathVariable username: String): EmployeeRole {
        return getEmployeeRoleByUsernameUseCase.runCatching { execute(username) }
                .withEntityExceptionsMapper()
                .getOrThrow()
    }

    @PostMapping("/{id}/availability-for-separate")
    fun setSeparateActivityAvailability(@PathVariable id: Long,
                                        @RequestBody available: List<String>): SeparateActivityAvailabilityDto {
        return setAvailableForSeparateActivitiesUseCase
                .runCatching { execute(id, available.map(stringDateMapper::map)) }
                .withEntityExceptionsMapper()
                .getOrThrow()
                .let(availabilityToDtoMapper::map)
    }

    @GetMapping("/{id}/availability-for-separate")
    fun getSeparateActivityAvailability(@PathVariable id: Long): SeparateActivityAvailabilityDto {
        return getAvailabilityForSeparateActivitiesUseCase
                .runCatching { execute(id) }
                .withEntityExceptionsMapper()
                .getOrThrow()
                .let(availabilityToDtoMapper::map)
    }
}