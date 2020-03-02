package ru.fors.employee.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.entity.EmployeeFilter
import ru.fors.employee.api.domain.usecase.*
import ru.fors.employee.data.dto.*
import ru.fors.employee.data.mapper.AvailabilityDtoEntityMapper
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeeRole
import ru.fors.pagination.api.domain.entity.Order
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.pagination.api.domain.entity.Sort
import ru.fors.workload.api.domain.dto.AllEmployeeWorkloadsDto
import ru.fors.workload.api.domain.mapper.AllEmployeeWorkloadsToDtoMapper
import ru.fors.workload.api.domain.usecase.GetAllEmployeeWorkloadsUseCase

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
        private val availabilityMapper: AvailabilityDtoEntityMapper,
        private val getAllEmployeeWorkloadsUseCase: GetAllEmployeeWorkloadsUseCase,
        private val allEmployeeWorkloadsToDtoMapper: AllEmployeeWorkloadsToDtoMapper,
        private val getPositionsUseCase: GetPositionsUseCase,
        private val getSkillsUseCase: GetSkillsUseCase,
        private val getSubdivisionsUseCase: GetSubdivisionsUseCase
) {

    @PostMapping("/add")
    fun add(@RequestBody employeeWithRoleDto: EmployeeWithRoleDto): Employee {
        return addEmployeeUseCase.execute(employeeWithRoleDto)
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

    @GetMapping
    fun getAll(
            @RequestParam page: Int,
            @RequestParam size: Int,
            @RequestParam(defaultValue = "ASCENDING") order: Order,
            @RequestParam(defaultValue = "id") orderBy: Array<String>
    ): Page<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute(PageRequest(page, size, Sort(order, orderBy.toList())))
    }

    @PostMapping
    fun filter(
            @RequestParam page: Int,
            @RequestParam size: Int,
            @RequestParam(defaultValue = "ASCENDING") order: Order,
            @RequestParam(defaultValue = "id") orderBy: Array<String>,
            @RequestBody filter: EmployeeFilter
    ): Page<FullEmployeeInfoDto> {
        return getFullEmployeesInfoUseCase.execute(PageRequest(page, size, Sort(order, orderBy.toList())), filter)
    }

    @GetMapping("/{id}/workload")
    fun getWorkload(@PathVariable id: Long): AllEmployeeWorkloadsDto {
        return getAllEmployeeWorkloadsUseCase.execute(id)
                .let(allEmployeeWorkloadsToDtoMapper::map)
    }

    @GetMapping("/positions")
    fun getPositions(): PositionsDto {
        return PositionsDto(getPositionsUseCase.execute())
    }

    @GetMapping("/skills")
    fun getSkills(): SkillsDto {
        return SkillsDto(getSkillsUseCase.execute())
    }

    @GetMapping("/subdivisions")
    fun getSubdivisions(): SubdivisionsDto {
        return SubdivisionsDto(getSubdivisionsUseCase.execute())
    }
}
