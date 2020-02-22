package ru.fors.employee.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.usecase.*
import ru.fors.employee.data.dto.AvailableDatesDto
import ru.fors.employee.data.dto.SeparateActivityAvailabilityDto
import ru.fors.employee.data.mapper.AvailabilityDtoEntityMapper
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeeRole
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest

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
        private val availabilityMapper: AvailabilityDtoEntityMapper
) {

    @PostMapping("/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        return addEmployeeUseCase.execute(employeeWithRoleDto)

    }

    @PostMapping("")
    fun getFullEmployeeInfo(@RequestBody pageRequest: PageRequest): Page<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute(pageRequest)
    }

    @PostMapping("/{id}/update")
    fun updateEmployee(@PathVariable id: Long, @RequestBody updateDto: UpdateEmployeeInfoDto): Employee {
        return updateEmployeeUseCase.execute(id, updateDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        deleteEmployeeUseCase.runCatching { execute(id) }
    }

    @GetMapping("/users/{username}")
    fun getEmployeeRoleByUsername(@PathVariable username: String): EmployeeRole {
        return getEmployeeRoleByUsernameUseCase.execute(username)

    }

    @PostMapping("/{id}/availability-for-separate")
    fun setSeparateActivityAvailability(@PathVariable id: Long,
                                        @RequestBody available: AvailableDatesDto): SeparateActivityAvailabilityDto {
        return setAvailableForSeparateActivitiesUseCase.execute(id, available.dates)
                .let(availabilityMapper::mapEntity)
    }

    @GetMapping("/{id}/availability-for-separate")
    fun getSeparateActivityAvailability(@PathVariable id: Long): SeparateActivityAvailabilityDto {
        return getAvailabilityForSeparateActivitiesUseCase.execute(id)
                .let(availabilityMapper::mapEntity)
    }
}